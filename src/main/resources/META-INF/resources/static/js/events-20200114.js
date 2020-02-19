const templateWithRsvp =
    `
        <div class="event-header">
            {{#partnerEvent}}
                <div class="partner-banner">Partner Event</div>
            {{/partnerEvent}}   
            <h3>
                <a href="{{link}}">
                    {{#partnerEvent}}{{eventGroupName}}: {{/partnerEvent}}{{name}} 
                </a>
            </h3>
        </div>
             
        <p class="venue">{{venue}}</p>
        <p class="date_time">{{eventDate}}</p>
        {{#rsvpLimit}}
            {{#eventFull}}
                <p class="rsvp">Event ausgebucht, trag dich auf der Warteliste ein!</p>
            {{/eventFull}}
            {{^eventFull}}
                <p class="rsvp">Noch {{openRsvp}} von {{rsvpLimit}} Plätzen frei!</p>
            {{/eventFull}}
        {{/rsvpLimit}}
    
        <p><a id="ical-link" href="{{iCalLink}}">Zum Kalender hinzufügen</a></p>
        <a id="show-{{id}}" href="#/" class="visible">Details</a>
        <a id="hide-{{id}}" href="#/" class="hidden">Weniger</a>
        <div id="details-{{id}}" class="hidden">{{{details}}}</div> 
        `;

const templateWithoutRsvp =
    `
         <div class="event-header">
            <h3>
                <a href="{{link}}">{{name}}</a>
            </h3>
        </div>
        <p class="venue">{{venue}}</p>
        <p class="date_time">{{eventDate}}</p>
        <a id="show-{{id}}" href="#/" class="visible">Details</a>
        <a id="hide-{{id}}" href="#/" class="hidden">Weniger</a>
        <div id="details-{{id}}" class="hidden">{{{details}}}</div>
        `;

function showDetails(eventId) {
    document.getElementById('details-' + eventId).className = 'visible';
    document.getElementById('hide-' + eventId).className = 'visible';
    document.getElementById('show-' + eventId).className = 'hidden';
}

function hideDetails(eventId) {
    document.getElementById('details-' + eventId).className = 'hidden';
    document.getElementById('hide-' + eventId).className = 'hidden';
    document.getElementById('show-' + eventId).className = 'visible';
}

function renderWithTemplate(event, template) {
    const content = Mustache.render(template, event);
    let li = document.createElement('li');
    if(event.partnerEvent) {
        li.className = 'single-event partner-event';
    } else {
        li.className = 'single-event';
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
            if(element.openRsvp < 1) {
                element.eventFull = true;
            }
            upcomingEvents.appendChild(renderWithTemplate(element, templateWithRsvp));
        });
    }

    for (let el of document.getElementsByClassName('visible')) {
        el.addEventListener("click", function (event) {
            let id = el.id.split("-")[1];
            showDetails(id);
        });
    }
    for (let el of document.getElementsByClassName('hidden')) {
        el.addEventListener("click", function (event) {
            let id = el.id.split("-")[1];
            hideDetails(id);
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

    for (let el of document.getElementsByClassName('visible')) {
        el.addEventListener("click", function (event) {
            let id = el.id.split("-")[1];
            showDetails(id);
        });
    }
    for (let el of document.getElementsByClassName('hidden')) {
        el.addEventListener("click", function (event) {
            let id = el.id.split("-")[1];
            hideDetails(id);
        });
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
