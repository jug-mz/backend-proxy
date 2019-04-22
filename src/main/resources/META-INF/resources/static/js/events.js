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

    event.localize = function() {
        return (text, render) => moment(render(text)).locale("de").format('LLLL');
    }

    let content = Mustache.render('<h3><a href="{{link}}">{{name}}</a></h3>' +
        '<p class="venue">{{venue}}</p>' +
        '<p class="date_time">{{#localize}}{{eventDate}}{{/localize}}</p>' +
        '<p class="rsvp">Noch {{openRsvp}} von {{rsvpLimit}} Plätzen frei!</p>' +
        '<a id="show-{{id}}" href="#/" class="visible" onclick="showDetails(\'{{id}}\')">Details</a>' +
        '<a id="hide-{{id}}" href="#/" class="hidden" onclick="hideDetails(\'{{id}}\')">Weniger</a>' +
        '<div id="details-{{id}}" class="hidden">{{{details}}}</div>', event);

    let li = document.createElement('li');
    li.className = 'single-event upcoming-event'
    li.innerHTML = content;
    return li;
}

function renderPastEvent(event) {

    event.localize = function() {
        return (text, render) => moment(render(text)).locale("de").format('LLLL');
    }

    let content = Mustache.render('<h3><a href="{{link}}">{{name}}</a></h3>' +
        '<p class="venue">{{venue}}</p>' +
        '<p class="date_time">{{#localize}}{{eventDate}}{{/localize}}</p>' +
        '<a id="show-{{id}}" href="#/" class="visible" onclick="showDetails(\'{{id}}\')">Details</a>' +
        '<a id="hide-{{id}}" href="#/" class="hidden" onclick="hideDetails(\'{{id}}\')">Weniger</a>' +
        '<div id="details-{{id}}" class="hidden">{{{details}}}</div>', event);

    let li = document.createElement('li');
    li.className = 'single-event upcoming-event'
    li.innerHTML = content;
    return li;
}

const renderUpcoming = async () => {
    const response = await fetch('/api/upcoming');
    const myJson = await response.json();
    let upcoming = document.getElementById('upcoming-event-list');
    myJson.forEach(element => upcoming.appendChild(renderUpcomingEvent(element)));
}

const renderPast = async () => {
    const response = await fetch('/api/past');
    const myJson = await response.json();
    let upcoming = document.getElementById('past-event-list');
    myJson.forEach(element => upcoming.appendChild(renderPastEvent(element)));
}

renderUpcoming();
renderPast();