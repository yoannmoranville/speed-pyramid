package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.PasswordEncryption;
import eu.speedbadminton.pyramid.security.PasswordGenerator;
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

/**
 * User: Yoann Moranville
 * Date: 16/09/2013
 *
 * @author Yoann Moranville
 */

@Controller
public class CreatePlayerController {
    private static final Logger LOG = Logger.getLogger(CreatePlayerController.class);

    @Autowired
    private PlayerService playerService;

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
}
