const picture1 = document.querySelector("#pictureOne");
const picture2 = document.querySelector("#pictureTwo");
const pictureContainer = document.querySelector("#pictureContainer");
const histogramContainer = document.querySelector("#histogramContainer");
const contentHeader = document.querySelector("#contentHeader");
const contentDescription = document.querySelector("#contentDescription");
const pictureNumber = document.querySelector("#pictureNumber");

const TOTAL_PICTURES = 60;
const MAX_PICTURES = 10;

const shownPictures = [];
const chosenPictures = [];
let pictureList = [];

// Add event listeners for pictures clicked
[picture1, picture2].forEach(pic => pic.addEventListener("click", () => choose(pic.src)));

onLoad();

// Functions to be called when the page loads
function onLoad() {
    initiatePictureList();
    loadNextPicture();
}

// Creates a list of al the picture IDs and shuffles it.
function initiatePictureList() {
    pictureList = Array.from({length: TOTAL_PICTURES}, (_, i) => i + 1).sort(() => 0.5 - Math.random());
    console.log(`Shuffled list: [${pictureList}]`);
}

// Loads the next set of pictures and randomizes the view order on the page
function loadNextPicture() {
    if (shownPictures.length < MAX_PICTURES) {
        const [src1, src2] = randomNumber(2) === 1
            ? [`img/human/fig_${addZeros(pictureList[0])}.png`, `img/computer/fig_${addZeros(pictureList[0])}.png`]
            : [`img/computer/fig_${addZeros(pictureList[0])}.png`, `img/human/fig_${addZeros(pictureList[0])}.png`];

        picture1.src = src1;
        picture2.src = src2;

        shownPictures.push(pictureList.shift());
        pictureNumber.textContent = `Guess ${shownPictures.length}/${MAX_PICTURES}`;
        console.log(`Shown pictures: ${shownPictures}`);
    } else {
        showResults();
    }
}

// Function to POST the results to the backend part of the application
// Also displays the histogram of all results
function showResults() {
    const correctGuesses = calculateCorrect();
    document.getElementById(`column-${correctGuesses}`).style.backgroundColor = "green";
    const url = document.body.getAttribute("dynamic-url");
    console.log(url)
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
        histogramContainer.style.display = "flex";
    })
    .catch(console.error);
}
