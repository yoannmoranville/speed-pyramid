package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import eu.speedbadminton.pyramid.utils.Result;
import eu.speedbadminton.pyramid.utils.ResultsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Yoann Moranville
 * Date: 18/11/2013
 *
 * @author Yoann Moranville
 */

@Controller
public class ConfirmResultsController {
    private static final Logger LOG = Logger.getLogger(ConfirmResultsController.class);

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @RequestMapping(value={"/confirmResults"}, method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("confirmResultsView");
        String validationId = request.getParameter("id");
        Match match = null;
        if(matchService.getMatchByValidationId(validationId) != null) {

            match.setValidationId(null);
            matchService.update(match);

            Player challenger = match.getChallenger();
            Player challengee = match.getChallengee();
            Result result = ResultsUtil.parseResultString(match.getResult());
            boolean isChallengerWinner = ResultsUtil.isChallengerWinner(result);
            if(isChallengerWinner) {
                playerService.swap(challenger, challengee);
            }
            playerService.sendEmailResults(challenger, challengee, isChallengerWinner, result);

            modelAndView.addObject("error", false);

        } else {
            modelAndView.addObject("error", true);
        }

        return modelAndView;
    }
}
