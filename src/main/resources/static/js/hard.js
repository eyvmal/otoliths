const picture = document.querySelector("#pictureHard");
const pictureContainer = document.querySelector("#pictureContainer");
const histogramContainer = document.querySelector("#histogramContainer");
const contentHeader = document.querySelector("#contentHeader");
const contentDescription = document.querySelector("#contentDescription");
const btnContainer = document.querySelector("#btnContainer");
const pictureNumber = document.querySelector("#pictureNumber");

const buttonHuman = document.querySelector("#humanBtn");
const buttonAI = document.querySelector("#aiBtn");

const TOTAL_PICTURES = 60;
const MAX_PICTURES = 10;

const shownPictures = [];
const chosenPictures = [];
let pictureList = [];

[buttonHuman, buttonAI].forEach(btn => btn.addEventListener("click", () => choose(picture.src)));

onLoad();

function onLoad() {
    initiatePictureList();
    loadNextPicture();
}

function initiatePictureList() {
    pictureList = Array.from({length: TOTAL_PICTURES}, (_, i) => i + 1).sort(() => 0.5 - Math.random());
    console.log(`Shuffled list: [${pictureList}]`);
}

function loadNextPicture() {
    if (shownPictures.length < MAX_PICTURES) {
        picture.src = `img/${randomNumber(2) === 1 ? 'human' : 'computer'}/fig_${addZeros(pictureList[0])}.png`;

        shownPictures.push(pictureList.shift());
        pictureNumber.textContent = `Guess ${shownPictures.length}/${MAX_PICTURES}`;
        console.log(`Shown pictures: ${shownPictures}`);
    } else {
        showResults();
    }
}

function showResults() {
    const correctGuesses = calculateCorrect();
    document.getElementById("column-" + correctGuesses).style.backgroundColor = "green";
    const url = document.body.getAttribute("dynamic-url");
    fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({correctGuesses, shownPictures, chosenPictures}),
    })
    .then((response) => {
        contentHeader.textContent = "Results";
        contentDescription.textContent =
            "The histogram is showing your score compared to other people. Your score is highlighted in green";
        pictureContainer.style.display = "none";
        btnContainer.style.display = "none";
        histogramContainer.style.display = "flex";
    })
    .catch(console.error);
}
