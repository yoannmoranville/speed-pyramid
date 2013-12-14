package eu.speedbadminton.pyramid.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */

@Document(collection = "Match")
public class Match {
    @Id
    private ObjectId id;
    @DBRef
    private Player challenger;
    @DBRef
    private Player challengee;

    private Date creation;

    private Date matchDate;

    private String validationId;

    private boolean confirmed;

    private Result result;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public String getValidationId() {
        return validationId;
    }

    public void setValidationId(String validationId) {
        this.validationId = validationId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Match [id=" + id + ", challenger=" + challenger + ", challengee=" + challengee + ", matchDate=" + matchDate + ", result=" + result + "]";
    }

    public boolean isChallengerWinner(){
        if (this.getResult()==null || !this.getResult().isResultCorrect()){
            return false;
        }

        return this.getResult().getMatchWinner().equals(this.getChallenger());

    }


}
