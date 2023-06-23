package no.eyvind.otoliths.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "otoliths", name = "hardresults")
public class HardResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private int score;
    private Date date;

    public HardResults() {}
    public HardResults(String username, int score, Date date) {
        this.username = username;
        this.score = score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "HardResults{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
