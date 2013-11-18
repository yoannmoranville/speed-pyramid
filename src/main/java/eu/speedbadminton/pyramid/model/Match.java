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
        "challenger" : 1,
        "challengee" : 2
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

    private Player challenger;

    private Player challengee;

    private Date creation;

    private Date matchDate;

    private String result;

    private String validationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getChallenger() {
        return challenger;
    }

    public void setChallenger(Player challenger) {
        this.challenger = challenger;
    }

    public Player getChallengee() {
        return challengee;
    }

    public void setChallengee(Player challengee) {
        this.challengee = challengee;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getValidationId() {
        return validationId;
    }

    public void setValidationId(String validationId) {
        this.validationId = validationId;
    }

    @Override
    public String toString() {
        return "Match [id=" + id + ", challenger=" + challenger + ", challengee=" + challengee + ", matchDate=" + matchDate + ", result=" + result + "]";
    }
}
