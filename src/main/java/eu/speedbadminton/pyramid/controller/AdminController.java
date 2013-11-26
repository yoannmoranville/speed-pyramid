package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.*;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private MatchService matchService;

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
        playerService.sendEmailDisablePlayer(player.getName(), player.getEmail());
        List<Match> openChallenge = matchService.getOpenChallenges(player);
        if(openChallenge.size() > 0) {
            Match match = openChallenge.get(0);
            matchService.delete(match);
        }
        return new RedirectView("viewPlayers.html");
    }

    @RequestMapping(value = "/enable_user", method = RequestMethod.POST)
    public View enableAccount(HttpServletRequest request) {
        String playerId = request.getParameter("id");
        Player player = playerService.getPlayerById(playerId);
        player.setPyramidPosition(playerService.getLastPlayerPosition() + 1);
        player.setEnabled(true);
        playerService.update(player);
        playerService.sendEmailEnablePlayer(player.getName(), player.getEmail());
        return new RedirectView("viewPlayers.html");
    }

    @RequestMapping(value={"/createPlayer"})
    public ModelAndView handleRequest() {
        return new ModelAndView("createPlayerView");
    }

    @RequestMapping(value = "/create_player_save", method = RequestMethod.POST)
    public ModelAndView createPerson(@ModelAttribute Player player, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("createPlayerView");
        PlayerValidator playerValidator = new PlayerValidator();
        playerValidator.validate(player, result);
        for(Player player1 : playerService.getPlayers()) {
            if(player1.getEmail().equals(player.getEmail())) {
                result.rejectValue("email", "Email already exists");
            }
        }
        if(result.hasErrors()) {
            if(result.hasFieldErrors("name"))
                modelAndView.addObject("error_name", result.getFieldError("name").getCode());
            if(result.hasFieldErrors("email"))
                modelAndView.addObject("error_email", result.getFieldError("email").getCode());
            return modelAndView;
        }

        String password = PasswordGenerator.getRandomString();
//        LOG.info("Password for "+player.getEmail()+" is "+password);
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
        return new ModelAndView("redirect:viewPlayers.html");
    }

    @RequestMapping(value = {"/createAdmin"})
    public ModelAndView viewAdmin() {
        if(playerService.getPlayers().size() == 0)
            return new ModelAndView("createAdminView");
        return null;
    }

    @RequestMapping(value = {"/saveAdmin"}, method = RequestMethod.POST)
    public ModelAndView handleRequest(@ModelAttribute Player player, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("createAdminView");
        PlayerValidator playerValidator = new PlayerValidator();
        playerValidator.validate(player, result);
        for(Player player1 : playerService.getPlayers()) {
            if(player1.getEmail().equals(player.getEmail())) {
                result.rejectValue("email", "Email already exists");
            }
        }
        if(StringUtils.isEmpty(player.getPassword())) {
            result.rejectValue("password", "Password can not be empty");
        }
        if(result.hasErrors()) {
            if(result.hasFieldErrors("name"))
                modelAndView.addObject("error_name", result.getFieldError("name").getCode());
            if(result.hasFieldErrors("email"))
                modelAndView.addObject("error_email", result.getFieldError("email").getCode());
            if(result.hasFieldErrors("password"))
                modelAndView.addObject("error_password", result.getFieldError("password").getCode());
            return modelAndView;
        }
        player.setPassword(PasswordEncryption.generateDigest(player.getPassword()));
        player.setEnabled(true);
        player.setRole(Player.Role.ADMIN);
        player.setPyramidPosition(1);
        playerService.create(player);
        return new ModelAndView("redirect:login.html");
    }

    @RequestMapping(value={"/viewPlayers"})
    public ModelAndView viewPlayer() {
        ModelAndView modelAndView = new ModelAndView("playerView");
        List<PlayerView> players = new ArrayList<PlayerView>();
        List<PlayerView> playersDisabled = new ArrayList<PlayerView>();
        for(Player player: playerService.getPlayers()) {
            if(player.isEnabled())
                players.add(new PlayerView(player, !SecurityContextContainer.checkAvailability(player.getId())));
            else
                playersDisabled.add(new PlayerView(player, !SecurityContextContainer.checkAvailability(player.getId())));
        }
        players.addAll(playersDisabled);
        modelAndView.addObject("players", players);
        return modelAndView;
    }


    public static class PlayerView {
        private Player player;
        private boolean inUse;

        public PlayerView(Player player, boolean inUse) {
            this.player = player;
            this.inUse = inUse;
        }

        public Player getPlayer() {
            return player;
        }

        public boolean isInUse() {
            return inUse;
        }
    }
}
