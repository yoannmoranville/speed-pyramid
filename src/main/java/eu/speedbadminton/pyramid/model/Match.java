package eu.speedbadminton.pyramid.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */

/**
    db.match.findOne() {
        "_id" : 1,
        "player1" : 1,
        "player2" : 2
        ...
    }

    db.player.findOne() {
        "_id" : 1,
        "name" : yoann
        ...
    }
 */

@Document(collection = "Match")
public class Match {
    @Id
    private String id;

    private Player player1;

    private Player player2;

    private Date matchDate;

    private String result; //Create a parser to read and write those results in a string

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "Match [id=" + id + ", player1=" + player1 + ", player2=" + player2 + ", matchDate=" + matchDate + ", result=" + result + "]";
    }
}
