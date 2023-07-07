const picture1 = document.querySelector("#pictureOne");
const picture2 = document.querySelector("#pictureTwo");
const pictureContainer = document.querySelector("#pictureContainer");
const histogramContainer = document.querySelector("#histogramContainer");
const contentHeader = document.querySelector("#contentHeader");
const contentDescription = document.querySelector("#contentDescription");

const maxPictures = 10;
const shownPictures = [];
const chosenPictures = [];

// Adding event listeners for clicking on the pictures
picture1.addEventListener("click", () => choose(picture1.src));
picture2.addEventListener("click", () => choose(picture2.src));

onLoad();

// What to do on page startup
function onLoad() {
    loadNextPicture();
}

// Loading next pictures for comparison
function loadNextPicture() {
    // Check if the max number of pictures is reached
    if (shownPictures.length < maxPictures) {
        // loop to generate a NEW number
        while (true) {
            let randomNum = randomNumber(60);
            randomNum = addZeros(randomNum); // Make 1-9 => 01-09

            // If number isn't new, try again
            if (!shownPictures.includes(randomNum)) {
                // Update pictures shown on the website
                // Also randomizes the order
                switch (randomNumber(2)) {
                    case 1:
                        picture1.src = `https://malde.org/otoliths/human%20annotated/fig_${randomNum}.png`;
                        picture2.src = `https://malde.org/otoliths/computer%20annotated/fig_${randomNum}.png`;
                        shownPictures.push(randomNum);
                        // console.log(shownPictures); // Debug!
                        break;
                    case 2:
                        picture2.src = `https://malde.org/otoliths/human%20annotated/fig_${randomNum}.png`;
                        picture1.src = `https://malde.org/otoliths/computer%20annotated/fig_${randomNum}.png`;
                        shownPictures.push(randomNum);
                        // console.log(shownPictures); // Debug!
                        break;
                }
            } else {
                // I believe this will fix double clicks
                chosenPictures.pop();
                break;
            }
            break; // Breaks the loop
        }
    } else {
        showResults();
    }
}

function choose(choice) {
    if (chosenPictures.length < 10) {
        chosenPictures.push(choice.includes("human") ? "correct" : "wrong");
        // console.log(chosenPictures); // Debug
    }
    // Load new pictures
    loadNextPicture();
}

// Fixes numbers below 10 to start with 0.
function addZeros(number) {
    return number > 9 ? number : "0" + number;
}

// Generates a random number within the range
function randomNumber(range) {
    return Math.floor(Math.random() * range) + 1;
}

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
