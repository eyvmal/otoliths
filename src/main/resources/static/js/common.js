// Some basic functions that are common for both easymode and hardmode
function choose(choice) {
    if (chosenPictures.length < MAX_PICTURES) {
        chosenPictures.push(choice.includes("human") ? "correct" : "wrong");
    }
    loadNextPicture();
}
function addZeros(number) {
    return number > 9 ? number : `0${number}`;
}
function randomNumber(range) {
    return Math.floor(Math.random() * range) + 1;
}
function calculateCorrect() {
    const correctGuesses = chosenPictures.filter(pic => pic === "correct").length;
    return correctGuesses === 0 ? 1 : correctGuesses;
}