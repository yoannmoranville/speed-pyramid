package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.PlayerService;
import eu.speedbadminton.pyramid.service.PyramidService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    private PyramidService pyramidService;
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/viewPyramid")
    public ModelAndView handleRequest() {
        ModelAndView modelAndView = new ModelAndView("pyramidView");

        List<Player> players = playerService.getPlayers();
        int pyramidRows = pyramidService.countPyramidRowsFromNumberOfPlayers(players.size());
        List<Integer> rowJumps = pyramidService.getAllRowJumps(players.size());

        modelAndView.addObject("players", players);
        modelAndView.addObject("pyramidRows", pyramidRows);
        modelAndView.addObject("rowJumps", rowJumps);

        return modelAndView;
    }

}
