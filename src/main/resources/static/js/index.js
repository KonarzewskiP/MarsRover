let userId = getUrlParameter('userId')
if (userId == null || userId === '') {
    userId = localStorage.getItem('userId')
    if (userId == null || userId === '') {
        document.getElementById('createUser').value = true;
    } else {
        /*    fetch('savedPreferences?userId=' + userId)
                .then(response => response.json())
                .then(jsonResponse => console.log(jsonResponse))*/
        window.location.href = '/?userId=' + userId
    }
}

if (userId != null && userId !== '') {
    localStorage.setItem('userId', userId);
    document.getElementById('userId').value = userId;
}

//to zmiennej marsApiButtons przypisuje wartosci z przycisku (button) których id zaczyna sie na marsApi.
//przez "document" modnosze sie do pliku .html
//querySelectorAll pobiera wszystkie elementy z dokumentu ktore posuja do selectora.
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']");


marsApiButtons.forEach(button => button.addEventListener('click', function () {
    const buttonId = this.id
    const roverId = buttonId.split('marsApi')[1]
    let apiData = document.getElementById('marsApiRoverData')
    apiData.value = roverId;

    document.getElementById('frmRoverType').submit()
}))

//By this method we are taking parameters from Url.
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    let regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    let results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

let marsRoverType = document.getElementById('marsApiRoverData').value

console.log(marsRoverType)

highlightBtnByRoverType(marsRoverType);
//Previously we were taking parameters from Url but since we fetch data from DB we need to take parameters from
//homeDto object in index.html. The same for marsRoverType
// let marsSol = getUrlParameter('marsSol')

let marsSol = document.getElementById('marsSol').value
if (marsSol == null && marsSol === '') {
    document.getElementById('marsSol').value = 1
}

function highlightBtnByRoverType(roverType) {
    if (roverType === '') {
        roverType = 'Opportunity'
    }
    document.getElementById('marsApi' + roverType).classList.remove('btn-secondary')
    document.getElementById('marsApi' + roverType).classList.add('btn-primary')
}