package eu.speedbadminton.pyramid.controller;

import com.google.gson.Gson;
import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.security.PasswordEncryption;
import eu.speedbadminton.pyramid.security.PasswordGenerator;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.security.SecurityContextContainer;
import eu.speedbadminton.pyramid.service.PlayerService;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.utils.ResultsUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */

@Controller
public class PlayerController {
    private static final Logger LOG = Logger.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value={"/viewPlayers"})
    public ModelAndView viewPlayer() {
        ModelAndView modelAndView = new ModelAndView("playerView");
        List<PlayerView> players = new ArrayList<PlayerView>();
        for(Player player: playerService.getPlayers()) {
            players.add(new PlayerView(player, !SecurityContextContainer.checkAvailability(player.getId())));
        }
        modelAndView.addObject("players", players);
        return modelAndView;
    }

    @RequestMapping(value={"/viewPlayerData"}, method= RequestMethod.GET)
    public ModelAndView viewPlayerData(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("playerDataView");
        if(SecurityContext.get() != null) {
            String id;
            if(SecurityContext.get().isAdmin() && request.getParameter("id") != null) {
                id = request.getParameter("id");
            } else {
                id = SecurityContext.get().getPlayerId();
            }
            Player player = playerService.getPlayerById(id);
            modelAndView.addObject("player", player);
            List<String> matchIds = new ArrayList<String>();
            List<Match> matches = playerService.getMatchesOfPlayer(player);
            for(Match match : matches) {
                if(match.getMatchDate() == null) {
                    matchIds.add(match.getId());
                } else if(match.getResult() != null && match.getValidationId() != null) {
                    if((player == match.getChallengee() && ResultsUtil.isChallengerWinner(match.getResult())) || (player == match.getChallenger() && !ResultsUtil.isChallengerWinner(match.getResult()))) {
                        modelAndView.addObject("matchNeedingConfirmation", match.getId());
                        modelAndView.addObject("matchNeedingConfirmationLink", SpeedbadmintonConfig.getLinkServer() + URLEncoder.encode(match.getValidationId()));
                    }
                }
            }
            modelAndView.addObject("matchesWithoutResults", new Gson().toJson(matchIds));
            modelAndView.addObject("matches", matches);
        }
        return modelAndView;
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

        player.setRole(Player.Role.NONE);
        player.setPyramidPosition(playerService.getLastPlayerPosition() + 1);
        if(org.springframework.util.StringUtils.hasText(player.getId())) {
            playerService.update(player);
        } else {
            playerService.create(player);
            // TODO create config variable to bypass email notification for local testing
            //playerService.sendEmailPassword(player.getName(), player.getEmail(), password);
        }
        return new RedirectView("viewPlayers.html");
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public View changePassword(HttpServletRequest request) {
        String oldpwd = request.getParameter("oldpassword");
        String newpwd = request.getParameter("newpassword");
        String newpwdrepeat = request.getParameter("newpasswordrepeat");
        boolean error = false;
        if(StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd) || StringUtils.isEmpty(newpwdrepeat)) {
            //todo: error
            error = true;
        } else if(!newpwd.equals(newpwdrepeat)) {
            //todo: error
            error = true;
        } else {
            Player player = playerService.getPlayerById(SecurityContext.get().getPlayerId());
            if(!player.getPassword().equals(PasswordEncryption.generateDigest(oldpwd))) {
                //todo: error
                error = true;
            } else {
                player.setPassword(PasswordEncryption.generateDigest(newpwd));
                playerService.update(player);
            }
        }
        if(error)
            return new RedirectView("viewPlayerData.html?error=password");
        //todo send email
        return new RedirectView("viewPlayerData.html");
    }

    @RequestMapping(value = "uploadpicture", method = RequestMethod.POST)
    public View uploadPicture(@RequestParam("avatar") MultipartFile file) {
        if(!file.isEmpty()) {
            if(!file.getContentType().equals("image/jpeg")) {
                //todo: error
                LOG.error("Not jpeg...");
                return new RedirectView("viewPlayerData.html");
            }
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                Integer width = image.getWidth();
                Integer height = image.getHeight();
                if(width > 100 && height > 150) {
                    //todo: error
                    LOG.error("Width or Height too big...");
                    return new RedirectView("viewPlayerData.html");
                }

                String playerId = SecurityContext.get().getPlayerId();
                File savedFile = new File(SpeedbadmintonConfig.getSavePathForAvatarFile() + playerId + ".jpg");
                file.transferTo(savedFile);
                Player player = playerService.getPlayerById(playerId);
                player.setAvatarPath(playerId + ".jpg");
                playerService.update(player);
            } catch( IOException e ) {
                //todo: error
            }
        }
        return new RedirectView("viewPlayerData.html");
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
