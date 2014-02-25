package eu.speedbadminton.pyramid.controller.ajax;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * User: yoannmoranville
 * Date: 25/02/14
 *
 * @author yoannmoranville
 */
@Controller
public class StatisticsAjaxController extends AjaxAbstractController {
    private static final Logger LOG = Logger.getLogger(StatisticsAjaxController.class);

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = {"/viewStatistics"}, method = RequestMethod.GET)
    public ModelAndView viewStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView("statisticsView");
        modelAndView.addObject("players", playerService.getPlayers());
        return modelAndView;
    }

    @RequestMapping(value = {"/getStatistics"}, method = RequestMethod.POST)
    public void getStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("playerId");
        Writer writer = null;
        try {
            writer = getResponseWriter(response);
            Player player = playerService.getPlayerById(new ObjectId(userId));
            writer.append("{\"playername\": \"").append(player.getName()).append("\", \"stats\": ").append(playerService.createPastPositionsJson(player)).append("}");

        } catch (IOException e) {
            LOG.error("Error...", e);
        } finally {
            closeWriter(writer);
        }
    }
}
