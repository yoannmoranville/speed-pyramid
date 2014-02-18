package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.security.SecurityService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class LoginController {
    private static final Logger LOG = Logger.getLogger(LoginController.class);

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        if(request.getParameter("error") != null)
            modelAndView.addObject("error", true);
        return modelAndView;
    }

    @RequestMapping(value = "/check_login", method = RequestMethod.POST)
    public View loginPerson(@ModelAttribute Player player, ModelMap model) throws Exception {
        SecurityService.LoginResult loginResult = SecurityService.login(player.getEmail(), player.getPassword());
        if (SecurityService.LoginResult.LoginResultType.LOGGED_IN.equals(loginResult.getType())){
            LOG.info("User '" + player.getEmail() + "' has logged in");
            return new RedirectView("viewPyramid.html");
        } else if (SecurityService.LoginResult.LoginResultType.INVALID_USERNAME_PASSWORD.equals(loginResult.getType())) {
            return new RedirectView("login.html?error=wrong");
        } else if (SecurityService.LoginResult.LoginResultType.DISABLED_USER.equals(loginResult.getType())) {
            return new RedirectView("login.html?error=disabled");
        }
        return null;
    }

    @RequestMapping(value = "/switch_to_player", method = RequestMethod.POST)
    public View changeToPlayerAccount(HttpServletRequest request) {
        SecurityService.LoginResult loginResult = SecurityService.loginAsPlayer(request.getParameter("id"));

        if (SecurityService.LoginResult.LoginResultType.LOGGED_IN.equals(loginResult.getType()))
            return new RedirectView("viewPyramid.html");
        return new RedirectView("login.html?error=true");
    }

    @RequestMapping(value={"/logout"}, method = RequestMethod.GET)
    public View handleRequestLogout(HttpServletRequest request) {
        if(SecurityContext.get() != null) {
            String email = SecurityContext.get().getEmailAddress();
            if(request.getParameter("parent") != null) {
                SecurityService.logout(true);
            } else {
                SecurityService.logout(false);
            }
            LOG.info("User '" + email + "' has logged out");
        }
        return new RedirectView("viewPyramid.html");
    }
}
