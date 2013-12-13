package eu.speedbadminton.pyramid.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: lukasgotter
 * Date: 29.11.13
 */
public class PyramidViewModel {

    List<PlayerViewModel> playerViewModelList;
    List<Match> lastOverallMatches = new ArrayList<Match>();
    List<Match> openChallenges = new ArrayList<Match>();
    List<Match> lastPlayerMatches = new ArrayList<Match>();
    List<Match> waitingForConfirmationMatches = new ArrayList<Match>();
    Match unconfirmedLostMatch;
    Match unconfirmedWaitingMatch;

    Player loggedPlayer;

    public PyramidViewModel(List<PlayerViewModel> playerViewModelList, Player loggedPlayer) {
        this.playerViewModelList = playerViewModelList;
        this.loggedPlayer = loggedPlayer;
    }

    public Match getLoggedPlayerChallenge() {
        PlayerViewModel p = findLoggedPlayerViewModel();
        if (p!=null){
            return findLoggedPlayerViewModel().getCurrentMatch();
        }
        return null;
    }

    public List<PlayerViewModel> getPlayerViewModelList() {
        return playerViewModelList;
    }

    public void setPlayerViewModelList(List<PlayerViewModel> playerViewModelList) {
        this.playerViewModelList = playerViewModelList;
    }

    public Player getLoggedPlayer() {
        return loggedPlayer;
    }

    public void setLoggedPlayer(Player loggedPlayer) {
        this.loggedPlayer = loggedPlayer;
    }

    public boolean isLoggedPlayerIsFree() {
        return getLoggedPlayerChallenge()==null;
    }

    public List<Match> getLastOverallMatches() {
        return lastOverallMatches;
    }

    public void setLastOverallMatches(List<Match> lastOverallMatches) {
        this.lastOverallMatches = lastOverallMatches;
    }

    public List<Match> getLastPlayerMatches() {
        return lastPlayerMatches;
    }

    public void setLastPlayerMatches(List<Match> lastPlayerMatches) {
        this.lastPlayerMatches = lastPlayerMatches;
    }

    public List<Match> getWaitingForConfirmationMatches() {
        return waitingForConfirmationMatches;
    }

    public void setWaitingForConfirmationMatches(List<Match> waitingForConfirmationMatches) {
        this.waitingForConfirmationMatches = waitingForConfirmationMatches;
    }

    public List<Match> getOpenChallenges() {
        return openChallenges;
    }

    public void setOpenChallenges(List<Match> openChallenges) {
        this.openChallenges = openChallenges;
    }

    private PlayerViewModel findLoggedPlayerViewModel(){
        if(loggedPlayer==null) return null;

        Iterator<PlayerViewModel> it = playerViewModelList.iterator();
        while (it.hasNext()){
            PlayerViewModel p = it.next();
            if (p.getPlayer().equals(loggedPlayer)){
                return p;
            }
        }
        return null;
    }

    public Match getUnconfirmedLostMatch() {
        return unconfirmedLostMatch;
    }

    public void setUnconfirmedLostMatch(Match unconfirmedLostMatch) {
        this.unconfirmedLostMatch = unconfirmedLostMatch;
    }

    public Match getUnconfirmedWaitingMatch() {
        return unconfirmedWaitingMatch;
    }

    public void setUnconfirmedWaitingMatch(Match unconfirmedWaitingMatch) {
        this.unconfirmedWaitingMatch = unconfirmedWaitingMatch;
    }
}
