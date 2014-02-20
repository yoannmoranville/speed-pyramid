package eu.speedbadminton.pyramid.security;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * User: yoannmoranville
 * Date: 20/02/14
 *
 * @author yoannmoranville
 */
public class SslInterceptor implements HandlerInterceptor {
    private static final String[] UNSECURE_PAGES = new String[]{"viewPyramid.html"};

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String page = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        final SecurityContext securityContext = SecurityContext.get();

        if((securityContext == null && Arrays.asList(UNSECURE_PAGES).contains(page)) || request.isSecure()) {
            return true;
        } else {
            response.sendRedirect("https://" + request.getServerName() + ":8443" + request.getRequestURI());
            return false;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}