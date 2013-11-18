package eu.speedbadminton.pyramid.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
    private static final String[] ALLOWED_PAGES = new String[]{"viewPyramid.html", "login.html", "check_login.html"};
    private static final Logger LOG = Logger.getLogger(SecurityInterceptor.class);
    private String loginPage;

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String page = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        final SecurityContext securityContext = SecurityContext.get();

        LOG.info("1 " + page);
        //For the confirmation of results page
        if(page.startsWith("confirmResults.html?id=")) {
            LOG.info("Ok");
            return true;
        }

        if(securityContext != null || Arrays.asList(ALLOWED_PAGES).contains(page)) {
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