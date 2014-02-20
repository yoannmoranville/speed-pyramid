package eu.speedbadminton.pyramid.security.tags;

import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.security.SecurityContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * User: yoannmoranville
 * Date: 20/02/14
 *
 * @author yoannmoranville
 */
public class DevelopmentTag extends SimpleTagSupport {
    private String var;

    @Override
    public void doTag() throws JspException, IOException {
        this.getJspContext().setAttribute(var, SpeedbadmintonConfig.isDev());
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}