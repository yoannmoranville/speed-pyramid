package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.service.MatchService;
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
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping("/viewMatches")
    public ModelAndView handleRequest() {
        ModelAndView modelAndView = new ModelAndView("matchView");

        List<Match> allOpenChallenges = matchService.getAllOpenChallenges();
        List<Match> allUnconfirmedMatches = matchService.getAllUnconfirmedMatch();
        List<Match> allLastMatchesWithResults = matchService.getAllLastMatchesWithResults();
        modelAndView.addObject("allOpenChallenges", allOpenChallenges);
        modelAndView.addObject("allUnconfirmedMatches", allUnconfirmedMatches);
        modelAndView.addObject("allLastMatchesWithResults", allLastMatchesWithResults);

//        List<Match> matches = matchService.getMatches();
//        modelAndView.addObject("matches", matches);
        return modelAndView;
    }

}
