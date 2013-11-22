package eu.speedbadminton.pyramid.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    private Player challenger;
    @DBRef
    private Player challengee;

//    private String challengerId;
//
//    private String challengeeId;
//
//    private String challengerName;
//
//    private String challengeeName;

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

//    public String getChallengerId() {
//        return challengerId;
//    }
//
//    public void setChallengerId(String challengerId) {
//        this.challengerId = challengerId;
//    }
//
//    public String getChallengeeId() {
//        return challengeeId;
//    }
//
//    public void setChallengeeId(String challengeeId) {
//        this.challengeeId = challengeeId;
//    }
//
//    public String getChallengerName() {
//        return challengerName;
//    }
//
//    public void setChallengerName(String challengerName) {
//        this.challengerName = challengerName;
//    }
//
//    public String getChallengeeName() {
//        return challengeeName;
//    }
//
//    public void setChallengeeName(String challengeeName) {
//        this.challengeeName = challengeeName;
//    }

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
