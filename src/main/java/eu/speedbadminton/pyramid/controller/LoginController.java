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
            if(SecurityContext.get().isAdmin())
                return new RedirectView("admin.html");
            return new RedirectView("viewPlayerData.html");
        } else if (SecurityService.LoginResult.LoginResultType.INVALID_USERNAME_PASSWORD.equals(loginResult.getType())) {
            return new RedirectView("login.html?error=true");
        }
        return null;
    }

    @RequestMapping(value={"/logout"}, method = RequestMethod.GET)
    public ModelAndView handleRequestLogout(HttpServletRequest request) {
        SecurityService.logout();
        return new ModelAndView("login");
    }
}
