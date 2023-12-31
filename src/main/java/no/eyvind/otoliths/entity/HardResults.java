package no.eyvind.otoliths.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class HardResults implements Serializable {
@JsonProperty("username")
    private String username;
    @JsonProperty("score")
    private int score;
    @JsonProperty("date")
    private String date;

    public HardResults() {}
    public HardResults(String username, int score, String date) {
        this.username = username;
        this.score = score;
        this.date = date;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HardResults{" +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
