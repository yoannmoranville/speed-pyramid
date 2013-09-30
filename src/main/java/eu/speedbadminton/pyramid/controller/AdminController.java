package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.security.SecurityContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value={"/admin"}, method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request) {
        if(SecurityContext.get().isAdmin())
            return new ModelAndView("admin");
        return null;
    }
}
