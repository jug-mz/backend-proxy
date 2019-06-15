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

function renderUpcomingEvent(event) {

    event.localize = function () {
        return (text, render) => moment(render(text)).locale("de").format('LLLL');
    }

    let content = Mustache.render(
        `
        <h3><a href="{{link}}">{{name}}</a></h3>
        <p class="venue">{{venue}}</p>
        <p class="date_time">{{#localize}}{{eventDate}}{{/localize}}</p>
        <p class="rsvp">Noch {{openRsvp}} von {{rsvpLimit}} Plätzen frei!</p>
        <a id="show-{{id}}" href="#/" class="visible" onclick="showDetails(\'{{id}}\')">Details</a>
        <a id="hide-{{id}}" href="#/" class="hidden" onclick="hideDetails(\'{{id}}\')">Weniger</a>
        <div id="details-{{id}}" class="hidden">{{{details}}}</div> 
        `, event);

    let li = document.createElement('li');
    li.className = 'single-event upcoming-event'
    li.innerHTML = content;
    return li;
}

function renderPastEvent(event) {

    event.localize = function () {
        return (text, render) => moment(render(text)).locale("de").format('LLLL');
    }

    let content = Mustache.render(
        `
        <h3><a href="{{link}}">{{name}}</a></h3>
        <p class="venue">{{venue}}</p>
        <p class="date_time">{{#localize}}{{eventDate}}{{/localize}}</p>
        <a id="show-{{id}}" href="#/" class="visible" onclick="showDetails(\'{{id}}\')">Details</a>
        <a id="hide-{{id}}" href="#/" class="hidden" onclick="hideDetails(\'{{id}}\')">Weniger</a>
        <div id="details-{{id}}" class="hidden">{{{details}}}</div>
        `,
        event);

    let li = document.createElement('li');
    li.className = 'single-event upcoming-event'
    li.innerHTML = content;
    return li;
}

function renderSponsorItem(event) {
    let content = Mustache.render(
        `
        <a href="{{url}}">
            <img src="img/sponsors/{{imgName}}" alt="{{name}}" width="167">
        </a>
        `,
        event
    );

    let li = document.createElement('li');
    li.className = 'sponsor-panel';
    li.innerHTML = content;
    return li;
}

const renderUpcoming = async () => {
    const response = await fetch('/api/meetup/upcoming');
    const upcomingEventsJson = await response.json();
    let upcomingEvents = document.getElementById('upcoming-event-list');
    upcomingEventsJson.forEach(element => upcomingEvents.appendChild(renderUpcomingEvent(element)));
}

const renderPast = async () => {
    const response = await fetch('/api/meetup/past');
    const pastEventsJson = await response.json();
    let pastEvents = document.getElementById('past-event-list');
    pastEventsJson.forEach(element => pastEvents.appendChild(renderPastEvent(element)));
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