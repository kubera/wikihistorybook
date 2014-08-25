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

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.AbstractGraphCreator;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.JsonGraphCreator;

@ManagedBean
@SessionScoped
@WebFilter(urlPatterns = { "/json-d3js/maxNodes", "/json-d3js/zoomScale", "/json-d3js/zoomEnabled",
        "/json-d3js/actualNodesEdges" })
public class JsonController extends AbstractStreamController implements Filter, Serializable {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(JsonController.class);

    @ManagedProperty("#{jsonGraphCreator}")
    private JsonGraphCreator jsonGraphCreator;

    @Override
    protected AbstractGraphCreator getGraphCreator() {
        return jsonGraphCreator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // get the controller from the session to save the attributes!
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        JsonController jsonController = (JsonController) session.getAttribute("jsonController");
        if (parameterExists("maxNodes", request)) {
            LOGGER.debug("maxNodes client side changed: " + request.getParameter("maxNodes"));
            jsonController.setMaxNodes(Integer.valueOf(request.getParameter("maxNodes")));
        }
        if (parameterExists("zoomScale", request)) {
            LOGGER.debug("zoomScale client side changed: " + request.getParameter("zoomScale"));
            jsonController.setZoomScale(request.getParameter("zoomScale"));
        }
        if (parameterExists("zoomEnabled", request)) {
            LOGGER.debug("zoomEnabled client side changed: " + request.getParameter("zoomEnabled"));
            jsonController.setZoomEnabled(Boolean.valueOf(request.getParameter("zoomEnabled")));
        }
        if (requestUrlContains("actualNodesEdges", request)) {
            int nodes = jsonController.actualNumberOfNodesInGraph;
            int edges = jsonController.actualNumberOfEdgesInGraph;
            LOGGER.debug(String.format("client updates actual number of nodes (%d) and edges (%d).", nodes, edges));
            byte[] dataInBytes = String.format("{ \"nodes\" : %d, \"edges\" : %d}", nodes, edges).getBytes();
            response.setContentLength(dataInBytes.length);
            IOUtils.write(dataInBytes, response.getOutputStream());
        }
        response.setContentType("application/json ");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public void setJsonGraphCreator(JsonGraphCreator jsonGraphCreator) {
        this.jsonGraphCreator = jsonGraphCreator;
    }

    private boolean parameterExists(String parameterName, ServletRequest request) {
        String param = request.getParameter(parameterName);
        return param != null && param.trim().length() > 0;
    }

    private boolean requestUrlContains(String requestUrlPart, ServletRequest request) {
        String url = ((HttpServletRequest)request).getRequestURL().toString();
        return url.contains(requestUrlPart);
    }

}
