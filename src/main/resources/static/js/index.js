let userId = getUrlParameter('userId')

if (userId == null || userId === '') {
    userId = localStorage.getItem('userId')
    if (userId == null || userId === '') {
        document.getElementById('createUser').value = true;
    } else {
        window.location.href = '/?userId=' + userId
    }
}

if (userId != null && userId !== '') {
    localStorage.setItem('userId', userId);
    document.getElementById('userId').value = userId;
}

let marsApiButtons = document.querySelectorAll("button[id*='marsApi']");


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

let marsRoverType = document.getElementById('marsApiRoverData').value

console.log(marsRoverType)

highlightBtnByRoverType(marsRoverType);

let marsSol = document.getElementById('marsSol').value
if (marsSol == null && marsSol === '') {
    document.getElementById('marsSol').value = 1
}

function highlightBtnByRoverType(roverType) {
    console.log(roverType);
    console.log('roverType');

    if (roverType === '') {
        roverType = 'Opportunity'
    }
    document.getElementById('marsApi' + roverType).classList.remove('btn-secondary')
    document.getElementById('marsApi' + roverType).classList.add('btn-primary')
}