//to zmiennej marsApiButtons przypisuje wartosci z przycisku (button) których id zaczyna sie na marsApi.
//przez "document" modnosze sie do pliku .html
//querySelectorAll pobiera wszystkie elementy z dokumentu ktore posuja do selectora.
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")


marsApiButtons.forEach(button => button.addEventListener('click', function () {
    const buttonId = this.id
    const roverId = buttonId.split('marsApi')[1]
    let apiData = document.getElementById('marsApiRoverData')
    apiData.value = roverId;

    document.getElementById('frmRoverType').submit()
}))

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    let regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    let results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

let marsRoverType = getUrlParameter('marsApiRoverData')

highlightBtnByRoverType(marsRoverType);

let marsSol = getUrlParameter('marsSol')
if (marsSol == null && marsSol === ''){
    document.getElementById('marsSol').value = 1
}

function highlightBtnByRoverType(roverType) {
    if (roverType === '') {
        roverType = 'Opportunity'
    }
    document.getElementById('marsApi' + roverType).classList.remove('btn-secondary')
    document.getElementById('marsApi' + roverType).classList.add('btn-primary')
}