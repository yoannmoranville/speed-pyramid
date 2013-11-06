package eu.speedbadminton.pyramid.utils;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class Set {
    private int pointOfChallenger;
    private int pointOfChallengee;

    public Set(int pointOfChallenger, int pointOfChallengee) {
        this.pointOfChallenger= pointOfChallenger;
        this.pointOfChallengee = pointOfChallengee;
    }

    public int getPointOfChallenger() {
        return pointOfChallenger;
    }

    public void setPointOfChallenger(int pointOfChallenger) {
        this.pointOfChallenger = pointOfChallenger;
    }

    public int getPointOfChallengee() {
        return pointOfChallengee;
    }

    public void setPointOfChallengee(int pointOfChallengee) {
        this.pointOfChallengee = pointOfChallengee;
    }
}
