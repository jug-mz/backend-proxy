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
    let upcomingEvents = document.getElementById('upcoming-event-list');
    upcomingEventsJson.forEach(element => upcomingEvents.appendChild(renderWithTemplate(element, templateUpcoming)));
}

const renderPast = async () => {
    const response = await fetch('/api/meetup/past');
    const pastEventsJson = await response.json();
    let pastEvents = document.getElementById('past-event-list');
    pastEventsJson.forEach(element => pastEvents.appendChild(renderWithTemplate(element, templatePast)));
}

renderUpcoming();
renderPast();