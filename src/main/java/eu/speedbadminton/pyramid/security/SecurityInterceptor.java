package eu.speedbadminton.pyramid.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Yoann Moranville
 */
public class SecurityInterceptor implements HandlerInterceptor {
    private static final String[] ALLOWED_PAGES = new String[]{"viewPyramid.html", "login.html", "check_login.html", "confirmResults.html", "createAdmin.html", "saveAdmin.html", "getUserMatchData.html"};
    private static final Logger LOG = Logger.getLogger(SecurityInterceptor.class);
    private String loginPage;

    @Autowired
    private PlayerService playerService;

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String page = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        final SecurityContext securityContext = SecurityContext.get();

        if(securityContext != null || Arrays.asList(ALLOWED_PAGES).contains(page)) {
            if(playerService.getPlayers().size() == 0  && !page.equals("createAdmin.html") && !page.equals("saveAdmin.html")) {
                response.sendRedirect("createAdmin.html");
            }
            return true;
        }
        response.sendRedirect(loginPage);
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}