package eu.speedbadminton.pyramid.utils;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class Set {
    private int pointOfPlayer1;
    private int pointOfPlayer2;

    public Set(int pointOfPlayer1, int pointOfPlayer2) {
        this.pointOfPlayer1 = pointOfPlayer1;
        this.pointOfPlayer2 = pointOfPlayer2;
    }

    public int getPointOfPlayer1() {
        return pointOfPlayer1;
    }

    public void setPointOfPlayer1(int pointOfPlayer1) {
        this.pointOfPlayer1 = pointOfPlayer1;
    }

    public int getPointOfPlayer2() {
        return pointOfPlayer2;
    }

    public void setPointOfPlayer2(int pointOfPlayer2) {
        this.pointOfPlayer2 = pointOfPlayer2;
    }
}
