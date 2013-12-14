package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Player;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class Result {

    private static final Logger LOG = Logger.getLogger(Result.class);

    @DBRef
    private Player player1;
    @DBRef
    private Player player2;

    private List<Set> sets = new ArrayList<Set>();

    public Result(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     *
     * @param nr Set Number first Set is 1
     * @return
     */
    public Set getSet(int nr){
        int index = nr - 1;

        if (sets.size()>index){
            return sets.get(nr-1);
        }
        return null;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        if (sets.size()>3){
            throw new IllegalArgumentException("Maximum of three Sets.");
        }
        this.sets = sets;
    }

    public Result addSet(Set set){
        if (set==null){
            throw new IllegalArgumentException("Set cannot be null.");
        }
        if ( ! (set.getPlayer1().equals(this.getPlayer1()) && set.getPlayer2().equals(this.getPlayer2()))){
            throw new IllegalArgumentException("The result and match players must be the same in same order.");
        }

        if (this.sets.size()>=3){
            throw new IllegalArgumentException("Maximum of three Sets.");
        }

        this.sets.add(set);
        return this;
    }

    /**
     * rule #1 Result is correct if set1 and set2 are valid/completed AND set3 is null AND both sets are won by same player
     * rule #2 Result is correct if set1, set2, set3 are valid AND set1 and set2 have not the same winner
     * @return
     */
    public boolean isResultCorrect() {
        if (getSet(1)==null||getSet(2)==null){
            return false;
        }
        // rule #1
        if (getSet(1).setCompleted() && getSet(2).setCompleted() && getSet(3) == null){
            return getSet(1).getWinner().equals(getSet(2).getWinner());
        }
        // rule #2
        else if (getSet(1).setCompleted() && getSet(2).setCompleted() && getSet(3)!=null && getSet(3).setCompleted()){ // all sets completed
            if (! getSet(1).getWinner().equals(getSet(2).getWinner())) { // if set1 and set2 won by different players
                return true;
            }
        }

        return false;
    }

    public Player getMatchWinner(){
        if (!isResultCorrect()) {
            return null;
        }

        if (getSet(1).getWinner().equals(getSet(2).getWinner())){
            return getSet(1).getWinner();
        } else {
            return getSet(3).getWinner();
        }

    }

    public Player getMatchLooser(){
        Player winner = getMatchWinner();
        if (player1.equals(winner)){
            return player2;
        } else if (player2.equals(winner)){
            return player1;
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

//    @Override
//    public String toString() {
//        if (sets.size()==0){
//            return "no sets played";
//        }
//        String result = "";
//        Iterator<Set> it = sets.iterator();
//        while (it.hasNext()){
//            result += it.toString();
//            if (it.hasNext()){
//                result += ",";
//            }
//        }
//        return result;
//    }
}
