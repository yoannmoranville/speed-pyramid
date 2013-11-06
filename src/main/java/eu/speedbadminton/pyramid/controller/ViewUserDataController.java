package eu.speedbadminton.pyramid.controller;

import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
@Controller
public class ViewUserDataController {
    private static final Logger LOG = Logger.getLogger(ViewUserDataController.class);

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value={"/viewPlayerData"}, method= RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("playerDataView");
        if(SecurityContext.get() != null) {
            String id;
            if(SecurityContext.get().isAdmin() && request.getParameter("id") != null) {
                id = request.getParameter("id");
            } else {
                id = SecurityContext.get().getPlayerId();
            }
            modelAndView.addObject("yourself", id);
            Player player = playerService.getPlayerById(id);
            modelAndView.addObject("player", player);
            List<String> matchIds = new ArrayList<String>();
            LOG.info(playerService.getMatchesOfPlayer(player).size());
            for(Match match : playerService.getMatchesOfPlayer(player)) {
                if(match.getMatchDate() == null) {
                    matchIds.add(match.getId());
                }
            }
            modelAndView.addObject("matchesWithoutResults", new Gson().toJson(matchIds));
            modelAndView.addObject("matches", playerService.getMatchesOfPlayer(player));
        }
        return modelAndView;
    }
}
