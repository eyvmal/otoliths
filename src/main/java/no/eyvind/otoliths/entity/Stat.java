package no.eyvind.otoliths.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
class Stat implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("correctGuesses")
    private int correctGuesses;
    @JsonProperty("wrongGuesses")
    private int wrongGuesses;

    public Stat() {}

    public Stat(int id) {
        this.id = id;
        this.correctGuesses = 0;
        this.wrongGuesses = 0;
    }

    public int getId() {
        return id;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public void incrementCorrectGuesses() {
        this.correctGuesses += 1;
    }

    public void incrementWrongGuesses() {
        this.wrongGuesses += 1;
    }
}
