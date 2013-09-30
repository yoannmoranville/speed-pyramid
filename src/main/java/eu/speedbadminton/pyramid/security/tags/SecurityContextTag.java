package eu.speedbadminton.pyramid.security.tags;

import eu.speedbadminton.pyramid.security.SecurityContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Yoann Moranville
 */
public class SecurityContextTag extends SimpleTagSupport {
    private String var;

    @Override
    public void doTag() throws JspException, IOException {
        this.getJspContext().setAttribute(var, SecurityContext.get());
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}