package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.PlayerService;
import eu.speedbadminton.pyramid.service.PyramidService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value={"/viewPyramid"}, method= RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("pyramidView");
        String id = request.getParameter("id");
        if(StringUtils.isNotEmpty(id)) {
            modelAndView.addObject("yourself", id);
            modelAndView.addObject("availables", playerService.getAvailablePlayerIds(id));
        }
        List<Player> players = playerService.getPlayers();
        modelAndView.addObject("players", players);
        return modelAndView;
    }

}
