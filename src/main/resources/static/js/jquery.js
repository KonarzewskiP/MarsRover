var entryWord = "Green is greener on the other side of fance".toUpperCase();

var numOfLetterInAlphabet = 26;
var letters = initializeArrWithLetters();

var sizeEntryWord = entryWord.length;

var hiddenEntryWord = "";

for (i = 0; i < sizeEntryWord; i++) {
    // W JS uzywamy funckcji charAt() do iterowania po poszczegolnych elementach napisu
    if (entryWord.charAt(i) === " ") {
        hiddenEntryWord = hiddenEntryWord + " ";
    } else {
        hiddenEntryWord = hiddenEntryWord + "-";
    }
}

function initializeArrWithLetters() {
    var array = new Array(numOfLetterInAlphabet);
    var letterCode = 65;

    for (i = 0; i < 26; i++) {
        array[i] = String.fromCharCode(letterCode++);
    }
    return array;
}

function printEntryWord() {
    document.getElementById("board").innerHTML = hiddenEntryWord;
}

window.onload = start;

function start() {
    getAlphabet();
    printEntryWord();
}

function getAlphabet() {
    var letterCode = 65;
    var alphabet = "";

    for (i = 0; i < 26; i++) {
        var element = "let" + i;
        alphabet +=
            '<div class="letter" onclick="check(' +
            i +
            ')" id="' +
            element +
            '">' +
            String.fromCharCode(letterCode++) +
            "</div>";

        if ((i + 1) % 6 === 0) {
            alphabet += '<div style="clear: both;"></div>';
        }
    }
    document.getElementById("alphabet").insertAdjacentHTML("beforeend", alphabet);
}

String.prototype.changeCharacter = function (at, character) {
    if (at > this.length - 1) {
        return this.toString();
    } else {
        return this.substr(0, at) + character + this.substr(at + 1);
    }
}

function check(num) {
    for (i = 0; i < sizeEntryWord; i++) {
        if (entryWord.charAt(i) === letters[num]) {
            hiddenEntryWord = hiddenEntryWord.changeCharacter(i,letters[num]);
        }
    }
}
