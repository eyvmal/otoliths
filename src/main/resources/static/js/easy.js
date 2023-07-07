const picture1 = document.querySelector("#pictureOne");
const picture2 = document.querySelector("#pictureTwo");
const pictureContainer = document.querySelector("#pictureContainer");
const histogramContainer = document.querySelector("#histogramContainer");
const contentHeader = document.querySelector("#contentHeader");
const contentDescription = document.querySelector("#contentDescription");

const TOTAL_PICTURES = 60;
const maxPictures = 10;
const shownPictures = [];
const chosenPictures = [];
let pictureList = [];

// Legger til eventlisteners for å klikke på bildene
picture1.addEventListener("click", () => choose(picture1.src));
picture2.addEventListener("click", () => choose(picture2.src));

onLoad();

// Funksjoner som skal kjøres ved innlastning
function onLoad() {
    initiatePictureList();
    loadNextPicture();
}

function initiatePictureList() {
    // Lager en liste over tilgjengelige bilder
    for(let i = 1; i <= TOTAL_PICTURES; i++) {
        pictureList.push(i);
    }
    // Stokker listen
    pictureList.sort((a, b) => 0.5 - Math.random());
    console.log("Shuffled list: [" + pictureList + "]"); // Debug
}

// Laster inn de neste bildene
function loadNextPicture() {
    // Sjekke om maks antall bilder er nådd
    if (shownPictures.length < maxPictures) {
        // Oppdaterer bildene på nettsiden
        // Bytter også om bilde1 og bilde2 tilfeldig
        switch (randomNumber(2)) {
            case 1:
                picture1.src = `https://malde.org/otoliths/human%20annotated/fig_${addZeros(pictureList[0])}.png`;
                picture2.src = `https://malde.org/otoliths/computer%20annotated/fig_${addZeros(pictureList[0])}.png`;
                break;
            case 2:
                picture2.src = `https://malde.org/otoliths/human%20annotated/fig_${addZeros(pictureList[0])}.png`;
                picture1.src = `https://malde.org/otoliths/computer%20annotated/fig_${addZeros(pictureList[0])}.png`;
                break;
        }

        shownPictures.push(pictureList[0]);
        pictureList.shift();
        console.log("Shown pictures: " + shownPictures); // Debug!
    } else {
        showResults();
    }
}

// Sjekker om bilde du klikket på er menneske eller AI
// Gjør dette ved å sjekke om bilde-linken inneholder "human" eller ikke
// Hvis bildene skal lagres lokalt må denne skrives om
function choose(choice) {
    if (chosenPictures.length < 10) {
        chosenPictures.push(choice.includes("human") ? "correct" : "wrong");
        // console.log(chosenPictures); // Debug
    }
    loadNextPicture();
}

// Legger til en 0 forran bilder med kun ett siffer
function addZeros(number) {
    return number > 9 ? number : "0" + number;
}

// Genererer et tilfeldig tall med maks som input
function randomNumber(range) {
    return Math.floor(Math.random() * range) + 1;
}

// Teller opp antall riktige
function calculateCorrect() {
    let correctGuesses = 0;
    for (let i = 0; i < chosenPictures.length; i++) {
        if (chosenPictures[i] === "correct") {
            correctGuesses += 1;
        }
    }
    if (correctGuesses === 0){
        correctGuesses += 1;
    }

    return correctGuesses;
}

// Sender resultatet videre til backend for å lagres i databasen
function showResults() {
    const correctGuesses = calculateCorrect();
    document.getElementById("column-" + correctGuesses).style.backgroundColor =
        "green";

    let url = "/";
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(correctGuesses),
    })
        .then((response) => {
            contentHeader.textContent = "Results";
            contentDescription.textContent =
                "The histogram is showing your score compared to other people. Your score is highlighted in green";

            pictureContainer.style.display = "none";
            histogramContainer.style.display = "flex";
        })
        .catch((error) => {
            console.error(error);
        });
}
