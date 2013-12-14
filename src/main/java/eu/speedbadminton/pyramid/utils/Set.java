package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Player;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class Set {

    @DBRef
    private Player player1;
    @DBRef
    private Player player2;

    private int pointsPlayer1;
    private int pointsPlayer2;

    public Set(Player player1, Player player2, int pointsPlayer1, int pointsPlayer2) {
        this.player2 = player2;
        this.player1 = player1;
        this.pointsPlayer1 = pointsPlayer1;
        this.pointsPlayer2 = pointsPlayer2;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public void setPointsPlayer1(int pointsPlayer1) {
        this.pointsPlayer1 = pointsPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void setPointsPlayer2(int pointsPlayer2) {
        this.pointsPlayer2 = pointsPlayer2;
    }

    /**
     * first case: set is complete with: 16 points and other player max 14 points.
     * @return
     */
    private boolean isSetHasSimpleWinner(){
        int pointsPlayer1 = this.getPointsPlayer1();
        int pointsPlayer2 = this.getPointsPlayer2();
        if (pointsPlayer1==16){
            if (pointsPlayer2>=0 && pointsPlayer2<=14){
                return true;
            }
        } else if (pointsPlayer2==16) {
            if (pointsPlayer1>=0 && pointsPlayer1<=14){
                return true;
            }
        }
        return false;
    }

    /**
     * second case: set is complete with: more than 16 points and other player exactly 2 points less.
     * @return
     */
    private boolean isSetHasOvertimeWinner(){
        int pointsPlayer1 = this.getPointsPlayer1();
        int pointsPlayer2 = this.getPointsPlayer2();

        if (pointsPlayer1>16){
            if(pointsPlayer1 - pointsPlayer2 == 2){
                return true;
            }
        } else if (pointsPlayer2>16){
            if(pointsPlayer2-pointsPlayer1==2){
                return true;
            }
        }

        return false;
    }

    public boolean setCompleted(){
        if (isSetHasSimpleWinner() || isSetHasOvertimeWinner()){
            return true;
        }
        return false;
    }

    /**
     * Get the winning player (in advance) null if draw.
     * @return
     */
    private Player getWinningPlayer(){
        if (getPointsPlayer2() > getPointsPlayer1()){
            return player2;
        } else if (getPointsPlayer1() > getPointsPlayer2()){
            return player1;
        }
        return null;
    }

    /**
     * This method returns the winner of the set or null if there is no winner (result is not complete/valid)
     * @return
     */
    public Player getWinner(){
        if (setCompleted()){
            return getWinningPlayer();
        }
        return null;
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

    @Override
    public String toString() {
        return pointsPlayer1 + ":"+pointsPlayer2;
    }
}
