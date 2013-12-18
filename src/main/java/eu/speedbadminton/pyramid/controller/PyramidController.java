package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.model.*;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */
@Controller
public class PyramidController {
    private static final Logger LOG = Logger.getLogger(PyramidController.class);

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @RequestMapping(value = {"/viewPyramid"}, method = RequestMethod.GET)
    public ModelAndView pyramidRequest(HttpServletRequest request){

        Player loggedPlayer = null;

        if(SecurityContext.get() != null) {
            String id = SecurityContext.get().getPlayerId();
            loggedPlayer = playerService.getPlayerById(id);
        }

        List<Player> players = playerService.getEnabledPlayers();
        Map<ObjectId, Player> challengeablePlayers = playerService.getChallengablePlayers(loggedPlayer);

        List<PlayerViewModel> playerViewModelList = new ArrayList<PlayerViewModel>();
        for(Player p : players){
            PlayerViewModel playerViewModel = new PlayerViewModel(p);

            List<Match> matchesOfPlayer = matchService.getMatchesOfPlayer(p);
            for (Match m : matchesOfPlayer) {
                if (m.getChallengee().equals(p) || m.getChallenger().equals(p)) {
                    if (m.getMatchDate() == null || !m.isConfirmed()) {
                        playerViewModel.setCurrentMatch(m);
                    } else {
                        if(playerViewModel.getPastMatches().size() <= 5)
                            playerViewModel.addPastMatch(m);
                    }
                }
            }
            boolean free = false;
            if (playerViewModel.getCurrentMatch()==null){
                // check if player is challengable
                free = challengeablePlayers.containsKey(p.getId());

            }
            playerViewModel.setFree(free);
            playerViewModel.setWonMatches(matchService.getMatchesWon(p));
            playerViewModel.setLostMatches(matchService.getMatchesLost(p));

            for(Match match : playerViewModel.getPastMatches()) {
                Result result = match.getResult();
                if(p.equals(match.getChallenger())) {
                    if(p.equals(result.getMatchWinner())) {
                        playerViewModel.setChallengerWonMatchesCount(playerViewModel.getChallengerWonMatchesCount() + 1);
                    } else {
                        playerViewModel.setChallengerLostMatchesCount(playerViewModel.getChallengerLostMatchesCount() + 1);
                    }
                } else if(p.equals(match.getChallengee())) {
                    if(p.equals(result.getMatchWinner())) {
                        playerViewModel.setChallengeeWonMatchesCount(playerViewModel.getChallengeeWonMatchesCount() + 1);
                    } else {
                        playerViewModel.setChallengeeLostMatchesCount(playerViewModel.getChallengeeLostMatchesCount() + 1);
                    }
                }
            }

            playerViewModelList.add(playerViewModel);
        }

        ModelAndView modelAndView = new ModelAndView("pyramidView");

        PyramidViewModel pyramidViewModel = new PyramidViewModel(playerViewModelList,loggedPlayer);
        pyramidViewModel.setUnconfirmedLostMatch(matchService.getUnconfirmedLostMatch(loggedPlayer));
        pyramidViewModel.setUnconfirmedWaitingMatch(matchService.getWaitingForConfirmationMatch(loggedPlayer));
        pyramidViewModel.setLastOverallMatches(matchService.getLastMatchesWithResults());
        pyramidViewModel.setLastPlayerMatches(matchService.getLastMatchesWithResults(loggedPlayer));
        pyramidViewModel.setOpenChallenges(matchService.getOpenChallenges());
        pyramidViewModel.setUnconfirmedMatches(matchService.getUnconfirmedMatches());
        modelAndView.addObject("pyramidViewModel", pyramidViewModel);
        modelAndView.addObject("avatarPath", SpeedbadmintonConfig.getPathForAvatarFile());
        modelAndView.addObject("isInChallengeDate", playerService.getDaysUntilTimeout(pyramidViewModel.getLoggedPlayerChallenge()));
        modelAndView.addObject("bestMale", playerService.getBestMale());
        modelAndView.addObject("bestFemale", playerService.getBestFemale());

        return modelAndView;
    }

}
