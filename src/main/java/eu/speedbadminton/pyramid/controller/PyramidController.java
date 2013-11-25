package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value={"/viewPyramid"}, method= RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("pyramidView");
        String id = "";
        if(SecurityContext.get() != null) {
            id = SecurityContext.get().getPlayerId();
            modelAndView.addObject("yourself", id);

            boolean isInChallenge = false;
            Date creationDate = null;
            for(Match match : playerService.getMatchesOfPlayer(playerService.getPlayerById(id))) {
                if(match.getMatchDate() == null || match.getValidationId() != null) {
                    isInChallenge = true;
                    creationDate = match.getCreation();
                    break;
                }
            }
            modelAndView.addObject("isInChallenge", isInChallenge);
            modelAndView.addObject("isInChallengeDate", playerService.getDaysUntilTimeout(creationDate));
            if(!isInChallenge) {
                modelAndView.addObject("available_players", playerService.getAvailablePlayers(id));
            }
        }
        List<Player> players = playerService.getEnabledPlayers();

        modelAndView.addObject("avatarPath", SpeedbadmintonConfig.getPathForAvatarFile());
        modelAndView.addObject("players", players);
        modelAndView.addObject("current_player_id",id);
        return modelAndView;
    }

}
