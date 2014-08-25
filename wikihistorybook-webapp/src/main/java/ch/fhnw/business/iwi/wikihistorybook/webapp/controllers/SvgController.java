package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.AbstractGraphCreator;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

@ManagedBean
@SessionScoped
@WebFilter(urlPatterns = {"/svg/maxNodes", "/svg/zoomScale", "/svg/zoomEnabled"})
public class SvgController extends AbstractStreamController implements Filter, Serializable {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(SvgController.class);

    @ManagedProperty("#{svgGraphCreator}")
    private SvgGraphCreator svgGraphCreator;

    @Override
    protected AbstractGraphCreator getGraphCreator() {
        return svgGraphCreator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // get the controller from the session to save the attributes!
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        SvgController svgController = (SvgController) session.getAttribute("svgController");
        if (parameterExists("maxNodes", request)) {
            LOGGER.debug("maxNodes client side changed: " + request.getParameter("maxNodes"));
            svgController.setMaxNodes(Integer.valueOf(request.getParameter("maxNodes")));
        }
        if (parameterExists("zoomScale", request)) {
            LOGGER.debug("zoomScale client side changed: " + request.getParameter("zoomScale"));
            svgController.setZoomScale(request.getParameter("zoomScale"));
        }
        if (parameterExists("zoomEnabled", request)) {
            LOGGER.debug("zoomEnabled client side changed: " + request.getParameter("zoomEnabled"));
            svgController.setZoomEnabled(Boolean.valueOf(request.getParameter("zoomEnabled")));
        }
        response.setContentType("text/plain");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public void setSvgGraphCreator(SvgGraphCreator svgGraphCreator) {
        this.svgGraphCreator = svgGraphCreator;
    }

    private boolean parameterExists(String parameterName, ServletRequest request) {
        String param = request.getParameter(parameterName);
        return param != null && param.trim().length() > 0;
    }

}
