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

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

@ManagedBean
@SessionScoped
@WebFilter(urlPatterns = {"/svg/maxNodes", "/svg/zoomScale", "/svg/zoomEnabled"})
public class SvgController implements Filter, Serializable {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(SvgController.class);

    private int year = 0;
    private int maxNodes = 1000;
    private String zoomScale = "0.2";
    private boolean zoomEnabled = false;

    @ManagedProperty("#{svgGraphCreator}")
    private SvgGraphCreator svgGraphCreator;

    public String getUniqueSvgName() {
        return svgGraphCreator.imageName(year, maxNodes);
    }

    public String getSvgStream() {
        return svgGraphCreator.getSvgStream(year, maxNodes).toString();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
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

    public String getMaxNodes() {
        LOGGER.debug("getMaxNodes " + this.hashCode());
        return String.valueOf(maxNodes);
    }

    public void setMaxNodes(int maxNodes) {
        LOGGER.debug("setMaxNodes " + this.hashCode());
        this.maxNodes = maxNodes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(String zoomScale) {
        this.zoomScale = zoomScale;
    }

    public boolean isZoomEnabled() {
        return zoomEnabled;
    }

    public void setZoomEnabled(boolean zoomEnabled) {
        this.zoomEnabled = zoomEnabled;
    }

    public void setSvgGraphCreator(SvgGraphCreator svgGraphCreator) {
        this.svgGraphCreator = svgGraphCreator;
    }

    private boolean parameterExists(String parameterName, ServletRequest request) {
        String param = request.getParameter(parameterName);
        return param != null && param.trim().length() > 0;
    }

}
