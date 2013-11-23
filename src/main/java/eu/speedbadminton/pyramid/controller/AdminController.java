package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.PasswordEncryption;
import eu.speedbadminton.pyramid.security.PasswordGenerator;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.security.SecurityService;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Yoann Moranville
 */
@Controller
public class AdminController {
    private static final Logger LOG = Logger.getLogger(AdminController.class);

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value={"/admin"}, method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        if(SecurityContext.get().isAdmin())
            return new ModelAndView("admin");
        return null;
    }

    @RequestMapping(value = "/disable_user", method = RequestMethod.POST)
    public View disableAccount(HttpServletRequest request) {
        String playerId = request.getParameter("id");
        Player player = playerService.getPlayerById(playerId);
        long positionDeletedPlayer = player.getPyramidPosition();

        playerService.addOnePositionToPlayers(positionDeletedPlayer);
        player.setPyramidPosition(-1);
        player.setEnabled(false);
        playerService.update(player);
        //todo: send EMAIL...
        return new RedirectView("viewPlayers.html");
    }

    @RequestMapping(value = "/enable_user", method = RequestMethod.POST)
    public View enableAccount(HttpServletRequest request) {
        String playerId = request.getParameter("id");
        Player player = playerService.getPlayerById(playerId);
        player.setPyramidPosition(playerService.getLastPlayerPosition() + 1);
        player.setEnabled(true);
        playerService.update(player);
        //todo: send EMAIL...
        return new RedirectView("viewPlayers.html");
    }

    @RequestMapping(value={"/createPlayer"})
    public ModelAndView handleRequest() {
        return new ModelAndView("createPlayerView");
    }

    @RequestMapping(value = "/create_player_save", method = RequestMethod.POST)
    public View createPerson(@ModelAttribute Player player, ModelMap model) {
        for(Player player1 : playerService.getPlayers()) {
            if(player1.getEmail().equals(player.getEmail())) {
                return new RedirectView("viewPlayers.html?error=true");
            }
        }

        String password = PasswordGenerator.getRandomString();
        player.setPassword(PasswordEncryption.generateDigest(password));

        player.setEnabled(true);
        player.setRole(Player.Role.NONE);
        player.setPyramidPosition(playerService.getLastPlayerPosition() + 1);
        if(StringUtils.hasText(player.getId())) {
            playerService.update(player);
        } else {
            playerService.create(player);
            playerService.sendEmailPassword(player.getName(), player.getEmail(), password);
        }
        return new RedirectView("viewPlayers.html");
    }

    @RequestMapping(value = {"/createAdmin"})
    public ModelAndView viewAdmin() {
        if(playerService.getPlayers().size() == 0)
            return new ModelAndView("createAdminView");
        return null;
    }

    @RequestMapping(value = {"/saveAdmin"}, method = RequestMethod.POST)
    public View handleRequest(@ModelAttribute Player player) {
        for(Player player1 : playerService.getPlayers()) {
            if(player1.getEmail().equals(player.getEmail())) {
                return new RedirectView("createAdminView.html?error=true");
            }
        }
        player.setPassword(PasswordEncryption.generateDigest(player.getPassword()));
        player.setEnabled(true);
        player.setRole(Player.Role.ADMIN);
        player.setPyramidPosition(1);
        playerService.create(player);
        // TODO create config variable to bypass email notification for local testing
        //playerService.sendEmailPassword(player.getName(), player.getEmail(), password);
        return new RedirectView("login.html");
    }
}
