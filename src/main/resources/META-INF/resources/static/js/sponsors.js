//Renders a single sponsors list on the right side of the screen
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

//Renders the list of sponsors on the right side of the screen
const renderSponsors = async () => {
    const response = await fetch('/api/sponsor?sortBy=random');
    const sponsorsJson = await response.json();
    let sponsors = document.getElementById('sponsor-list');
    sponsorsJson.forEach(element => sponsors.appendChild(renderSponsorItem(element)));
}

renderSponsors();