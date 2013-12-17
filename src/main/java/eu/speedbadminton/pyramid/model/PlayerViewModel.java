package eu.speedbadminton.pyramid.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lukasgotter
 * Date: 28.11.13
 */
public class PlayerViewModel {

    Player player;
    boolean free;
    Match currentMatch;
    int wonMatches;
    int lostMatches;
    List<Match> pastMatches = new ArrayList<Match>();

    public PlayerViewModel(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(Match currentMatch) {
        this.currentMatch = currentMatch;
    }

    public List<Match> getPastMatches() {
        return pastMatches;
    }

    public void setPastMatches(List<Match> pastMatches) {
        this.pastMatches = pastMatches;
    }

    public void addPastMatch(Match match){
        this.pastMatches.add(match);
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(int wonMatches) {
        this.wonMatches = wonMatches;
    }

    public int getLostMatches() {
        return lostMatches;
    }

    public void setLostMatches(int lostMatches) {
        this.lostMatches = lostMatches;
    }

    public Player getCurrentOpponent(){
        if (currentMatch==null) return null;

        if (!currentMatch.getChallengee().equals(this.player)) {
            return currentMatch.getChallengee();
        }  else {
            return currentMatch.getChallenger();
        }

    }


}
