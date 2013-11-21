package eu.speedbadminton.pyramid.starter;

import eu.speedbadminton.pyramid.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Yoann Moranville
 * Date: 20/11/2013
 *
 * @author Yoann Moranville
 */
public class StarterInterceptor implements HandlerInterceptor {

    @Autowired
    private PlayerService playerService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(playerService.getPlayers().size() == 0) {
            //send page for creation of admin
            httpServletResponse.sendRedirect("createAdmin.html"); //todo
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
