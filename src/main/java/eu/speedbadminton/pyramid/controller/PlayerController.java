package eu.speedbadminton.pyramid.controller;

import com.google.gson.Gson;
import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.security.PasswordEncryption;
import eu.speedbadminton.pyramid.security.PasswordGenerator;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.security.SecurityContextContainer;
import eu.speedbadminton.pyramid.service.MatchService;
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

    @RequestMapping(value={"/viewPlayerData"}, method= RequestMethod.GET)
    public ModelAndView viewPlayerData() {
        ModelAndView modelAndView = new ModelAndView("playerDataView");
        if(SecurityContext.get() != null) {
            Player player = playerService.getPlayerById(SecurityContext.get().getPlayerId());
            if(player != null)
                modelAndView.addObject("player", player);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ModelAndView changePassword(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("playerDataView");
        String oldpwd = request.getParameter("oldpassword");
        String newpwd = request.getParameter("newpassword");
        String newpwdrepeat = request.getParameter("newpasswordrepeat");
        if(StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd) || StringUtils.isEmpty(newpwdrepeat)) {
            modelAndView.addObject("errorpwd", "All fields need to be filled.");
        } else if(!newpwd.equals(newpwdrepeat)) {
            modelAndView.addObject("errorpwd", "The new passwords do not match.");
        } else {
            Player player = playerService.getPlayerById(SecurityContext.get().getPlayerId());
            if(!player.getPassword().equals(PasswordEncryption.generateDigest(oldpwd))) {
                modelAndView.addObject("errorpwd", "The old password does not match the current one.");
            } else {
                player.setPassword(PasswordEncryption.generateDigest(newpwd));
                playerService.update(player);
                playerService.sendEmailChangePassword(player.getName(), player.getEmail());
                modelAndView.addObject("changepassword", "true");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "uploadpicture", method = RequestMethod.POST)
    public ModelAndView uploadPicture(@RequestParam("avatar") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("playerDataView");

        if(!file.isEmpty()) {
            if(!file.getContentType().equals("image/jpeg")) {
                LOG.error("Not jpeg...");
                modelAndView.addObject("erroravatar", "The file is not a JPG image.");
                return modelAndView;
            }
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                Integer width = image.getWidth();
                Integer height = image.getHeight();
                if(width > 100 && height > 150) {
                    LOG.error("Width or Height too big...");
                    modelAndView.addObject("erroravatar", "The file is not of the correct size.");
                    return modelAndView;
                }
                String playerId = SecurityContext.get().getPlayerId();
                File savedFile = new File(SpeedbadmintonConfig.getSavePathForAvatarFile() + playerId + ".jpg");
                file.transferTo(savedFile);
                Player player = playerService.getPlayerById(playerId);
                player.setAvatarPath(playerId + ".jpg");
                playerService.update(player);
                modelAndView.addObject("uploadpicture", "true");
            } catch( IOException e ) {
                modelAndView.addObject("erroravatar", "An error occurred when uploading a picture, please contact the administrator.");
            }
        }
        return modelAndView;
    }
}
