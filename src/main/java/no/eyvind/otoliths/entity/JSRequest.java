package no.eyvind.otoliths.entity;

public class JSRequest {
    private int correctGuesses;
    private int[] shownPictures;
    private String[] chosenPictures;

    // getters and setters
    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public void setCorrectGuesses(int correctGuesses) {
        this.correctGuesses = correctGuesses;
    }
    public int[] getShownPictures() {
        return shownPictures;
    }

    public void setShownPictures(int[] shownPictures) {
        this.shownPictures = shownPictures;
    }

    public String[] getChosenPictures() {
        return chosenPictures;
    }

    public void setChosenPictures(String[] chosenPictures) {
        this.chosenPictures = chosenPictures;
    }
}
