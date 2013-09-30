package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.SecurityContext;
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
    private PlayerService playerService;

    @RequestMapping(value={"/viewPyramid"}, method= RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("pyramidView");
        if(SecurityContext.get() != null) {
            String id;
            if(SecurityContext.get().isAdmin() && request.getParameter("id") != null) {
                id = request.getParameter("id");
            } else {
                id = SecurityContext.get().getPlayerId();
            }
            modelAndView.addObject("yourself", id);
            String ids = playerService.getAvailablePlayerIds(id);
            modelAndView.addObject("availables", ids);
        }
        List<Player> players = playerService.getPlayers();
        modelAndView.addObject("players", players);
        return modelAndView;
    }

}
