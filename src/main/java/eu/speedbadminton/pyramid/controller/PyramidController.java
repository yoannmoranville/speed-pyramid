package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.model.PlayerViewModel;
import eu.speedbadminton.pyramid.model.PyramidViewModel;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
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
        List<Match> matches = matchService.getMatches();
        Map<String, Player> challengeablePlayers = playerService.getChallengablePlayers(loggedPlayer);

        List<PlayerViewModel> playerViewModelList = new ArrayList<PlayerViewModel>();
        for(Player p : players){
            PlayerViewModel playerViewModel = new PlayerViewModel(p);

            // add all matches for each player where he is eighter challenger or challangee
            for (Match m : matches) {
                if (m.getChallengee().equals(p) || m.getChallenger().equals(p)) {
                    if (m.getMatchDate()==null){
                        playerViewModel.setCurrentMatch(m);
                    } else {
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

            playerViewModelList.add(playerViewModel);
        }

        ModelAndView modelAndView = new ModelAndView("pyramidView");

        PyramidViewModel pyramidViewModel = new PyramidViewModel(playerViewModelList,loggedPlayer);
        pyramidViewModel.setLastOverallMatches(matchService.getLastMatchesWithResults());
        pyramidViewModel.setLastPlayerMatches(matchService.getLastMatchesWithResults(loggedPlayer));
        pyramidViewModel.setWaitingForConfirmationMatches(matchService.getWaitingForConfirmationMatches(loggedPlayer));
        pyramidViewModel.setOpenChallenges(matchService.getOpenChallenges());
        modelAndView.addObject("pyramidViewModel", pyramidViewModel);
        modelAndView.addObject("avatarPath", SpeedbadmintonConfig.getPathForAvatarFile());

        modelAndView.addObject("isInChallengeDate", playerService.getDaysUntilTimeout(pyramidViewModel.getLoggedPlayerChallenge()));

        return modelAndView;
    }

}
