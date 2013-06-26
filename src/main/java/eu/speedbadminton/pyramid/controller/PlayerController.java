package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.service.PlayerService;
import eu.speedbadminton.pyramid.model.Player;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */

@Controller
public class PlayerController {
    private static final Logger LOG = Logger.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value={"/viewPlayers"}, method= RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView("playerView");

        List<Player> players;
        if(StringUtils.isNotEmpty(id)) {
            players = new ArrayList<Player>(1);
            Player player = playerService.getPlayerById(Long.parseLong(id));
            players.add(player);
        } else {
            players = playerService.getPlayers();
        }
        modelAndView.addObject("players", players);
        return modelAndView;
    }
}
