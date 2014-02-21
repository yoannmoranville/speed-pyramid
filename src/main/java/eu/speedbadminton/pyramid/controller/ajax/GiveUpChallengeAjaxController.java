package eu.speedbadminton.pyramid.controller.ajax;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.model.Result;
import eu.speedbadminton.pyramid.model.Set;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: yoannmoranville
 * Date: 21/02/14
 *
 * @author yoannmoranville
 */
@Controller
public class GiveUpChallengeAjaxController extends AjaxAbstractController {
    private static final Logger LOG = Logger.getLogger(GiveUpChallengeAjaxController.class);
    @Autowired
    private MatchService matchService;
    @Autowired
    private PlayerService playerService;

    @RequestMapping(value={"/giveupChallenge"}, method = RequestMethod.POST)
    public void giveupChallenge(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        if(SecurityContext.get() == null) {
            throw new IllegalAccessError("Please authenticate");
        }

        Writer writer = getResponseWriter(response);
        try {
            String matchId = request.getParameter("matchid");
            Match match = matchService.getMatchById(matchId);

            Player loggedPlayer = playerService.getPlayerById(SecurityContext.get().getPlayerId());

            Player player1 = match.getChallenger();
            Player player2 = match.getChallengee();

            Result result = new Result(player1, player2).addSet(new Set(player1, player2, 16, 0)).addSet(new Set(player1, player2, 16, 0));

            DateTime dateTime = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
            Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(formatter.print(dateTime));

            match.setMatchDate(date);
            playerService.processResult(match, result, loggedPlayer, player1, player2);
            writeSimpleData(writer, "success", "true");
            response.setStatus(200);

        } catch (Exception e) {
            LOG.error("Error when giving up", e);
            response.setStatus(400);
        }
        closeWriter(writer);
    }
}
