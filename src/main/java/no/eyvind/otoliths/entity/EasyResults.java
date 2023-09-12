package no.eyvind.otoliths.entity;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;
import java.util.Date;

public class EasyResults implements Serializable {
    @JsonProperty("username")
    private String username;
    @JsonProperty("score")
    private int score;
    @JsonProperty("date")
    private Date date;

    public EasyResults() {}
    public EasyResults( String username, int score, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EasyResults{" +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
