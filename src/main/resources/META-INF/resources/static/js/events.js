const templateUpcoming =
    `
        <h3><a href="{{link}}">{{name}}</a></h3>
        <p class="venue">{{venue}}</p>
        <p class="date_time">{{eventDate}}</p>
        <p class="rsvp">Noch {{openRsvp}} von {{rsvpLimit}} Plätzen frei!</p>
        <a id="show-{{id}}" href="#/" class="visible" onclick="showDetails(\'{{id}}\')">Details</a>
        <a id="hide-{{id}}" href="#/" class="hidden" onclick="hideDetails(\'{{id}}\')">Weniger</a>
        <div id="details-{{id}}" class="hidden">{{{details}}}</div> 
        `;

const templatePast =
    `
        <h3><a href="{{link}}">{{name}}</a></h3>
        <p class="venue">{{venue}}</p>
        <p class="date_time">{{eventDate}}</p>
        <a id="show-{{id}}" href="#/" class="visible" onclick="showDetails(\'{{id}}\')">Details</a>
        <a id="hide-{{id}}" href="#/" class="hidden" onclick="hideDetails(\'{{id}}\')">Weniger</a>
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
    let content = Mustache.render(template, event);
    let li = document.createElement('li');
    li.className = 'single-event'
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
        upcomingEventsJson.forEach(element => upcomingEvents.appendChild(renderWithTemplate(element, templateUpcoming)));
    }
}

const renderPast = async () => {
    const response = await fetch('/api/meetup/past');
    const pastEventsJson = await response.json();
    if(pastEventsJson.length > 0) {
        let pastEvents = document.getElementById('past-event-list');
        let noContentBanner = document.getElementById('no-past');
        pastEvents.removeChild(noContentBanner);
        pastEventsJson.forEach(element => pastEvents.appendChild(renderWithTemplate(element, templatePast)));
    }

}

function renderSponsorItem(event) {
    let template =
        `
        <a href="{{url}}">
            <img src="img/sponsors/{{imgName}}" alt="{{name}}" width="167">
        </a>
        `

    let content = Mustache.render(template, event);
    let li = document.createElement('li');
    li.className = 'sponsor-panel';
    li.innerHTML = content;
    return li;
}

const renderSponsors = async () => {
    const response = await fetch('/api/sponsor?sortBy=random');
    const sponsorsJson = await response.json();
    let sponsors = document.getElementById('sponsor-list');
    sponsorsJson.forEach(element => sponsors.appendChild(renderSponsorItem(element)));
}

renderUpcoming();
renderPast();
renderSponsors();