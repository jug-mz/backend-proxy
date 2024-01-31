const templateWithRsvp = `
        {{#partnerEvent}}
            <span class="partner-badge">
                <small>Partner Event</small>
            </span>
        {{/partnerEvent}}   
        <hgroup class="event-header" id="{{id}}">
            <p>{{eventDate}} | {{venue}}</p>
            <h3>
                {{#partnerEvent}}«{{eventGroupName}}» {{/partnerEvent}}{{name}}  <a href="/#{{id}}">#</a>
            </h3>
        </hgroup>
        
        
        <p>
           <a href="{{link}}" target="_blank">Teilnehmen auf Meetup.com</a>
        </p>
             
        {{#rsvpLimit}}
            {{#eventFull}}
                <p class="rsvp">Event ausgebucht, trag dich auf der Warteliste ein!</p>
            {{/eventFull}}
            {{^eventFull}}
                <p class="rsvp">Noch {{openRsvp}} von {{rsvpLimit}} Plätzen frei!</p>
            {{/eventFull}}
        {{/rsvpLimit}}
    
        <p><a id="ical-link" href="{{iCalLink}}">Termin speichern</a></p>
        <details>
            <summary>Details</summary>
            <div>
            {{{details}}}
            </div>
        </details>
`;


const templateWithoutRsvp = `
     <hgroup id="{{id}}">
        <p>{{eventDate}} | {{venue}}</p>
        <h3>{{name}} <a href="/#{{id}}">#</a></h3>
    </hgroup>
    
    <p>
       <a href="{{link}}" target="_blank">Event auf Meetup.com</a>
    </p>
    
    <details>
        <summary>Details</summary>
        <div>
        {{{details}}}
        </div>
    </details>`;


function renderWithTemplate(event, template) {
    const content = Mustache.render(template, event);
    let li = document.createElement('li');
    if (event.partnerEvent) {
        li.className = 'event partner-event';
    } else {
        li.className = 'event';
    }
    li.innerHTML = content;
    return li;
}

const renderUpcoming = async () => {
    const response = await fetch('/api/meetup/upcoming');
    const upcomingEventsJson = await response.json();
    if (upcomingEventsJson.length > 0) {
        let upcomingEvents = document.getElementById('upcoming-event-list');
        let noContentBanner = document.getElementById('no-upcoming');
        upcomingEvents.removeChild(noContentBanner);
        upcomingEventsJson.forEach(element => {
            if (element.openRsvp < 1) {
                element.eventFull = true;
            }
            element.details = element.details.split('\\').join('')
            upcomingEvents.appendChild(renderWithTemplate(element, templateWithRsvp));
        });
    }
};

const renderPast = async () => {
    const response = await fetch('/api/meetup/past');
    const pastEventsJson = await response.json();
    if (pastEventsJson.length > 0) {
        let pastEvents = document.getElementById('past-event-list');
        let noContentBanner = document.getElementById('no-past');
        pastEvents.removeChild(noContentBanner);
        pastEventsJson.forEach(element => pastEvents.appendChild(renderWithTemplate(element, templateWithoutRsvp)));
    }
};

function renderSponsorItem(event) {
    const template =
        `
        <a href="{{url}}">
            <img src="img/sponsors/{{imgName}}" alt="{{name}}" width="167">
        </a>
        `;

    const content = Mustache.render(template, event);
    const li = document.createElement('li');
    li.className = 'sponsor-panel';
    li.innerHTML = content;
    return li;
}

function renderPartnerItem(event) {
    const template =
        `
        <a href="{{url}}">
            <img src="img/partners/{{imgName}}" alt="{{name}}" width="167">
        </a>
        `;

    const content = Mustache.render(template, event);
    const li = document.createElement('li');
    li.className = 'partner-panel';
    li.innerHTML = content;
    return li;
}

const renderSponsors = async () => {
    const response = await fetch('/api/sponsor?sortBy=random');
    const sponsorsJson = await response.json();
    const sponsors = document.getElementById('sponsor-list');
    sponsorsJson.forEach(element => sponsors.appendChild(renderSponsorItem(element)));
};

const renderPartners = async () => {
    const response = await fetch('/api/partner?sortBy=random');
    const partnersJson = await response.json();
    const partners = document.getElementById('partner-list');
    partnersJson.forEach(element => partners.appendChild(renderPartnerItem(element)));
};

renderUpcoming();
renderPast();
renderSponsors();
renderPartners();
