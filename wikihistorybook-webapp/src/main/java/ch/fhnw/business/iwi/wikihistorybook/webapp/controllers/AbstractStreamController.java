package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.AbstractGraphCreator;

public abstract class AbstractStreamController implements Filter {

    private final static Logger LOGGER = Logger.getLogger(AbstractStreamController.class);
    
    protected int year = 0;
    protected int maxNodes = 1000;
    protected String zoomScale = "0.2";
    protected boolean zoomEnabled = false;
    protected int actualNumberOfNodesInGraph;
    protected int actualNumberOfEdgesInGraph;

    protected abstract AbstractGraphCreator getGraphCreator();
    protected abstract AbstractStreamController getStreamController(HttpSession session);
    protected abstract String getContentType();

    public String getUniqueName() {
        GraphData graphInfo = new GraphData(year, maxNodes);
        String name = getGraphCreator().createStreamAndStore(graphInfo);
        keepGraphInfo(graphInfo);
        return name;
    }

    public String getStream() {
        GraphData graphInfo = new GraphData(year, maxNodes);
        ByteArrayOutputStream stream = getGraphCreator().getStream(graphInfo);
        keepGraphInfo(graphInfo);
        return stream.toString();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // get the controller from the session to save the attributes!
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        AbstractStreamController streamController = getStreamController(session);
        if (parameterExists("maxNodes", request)) {
            LOGGER.debug("maxNodes client side changed: " + request.getParameter("maxNodes"));
            streamController.setMaxNodes(Integer.valueOf(request.getParameter("maxNodes")));
        }
        if (parameterExists("zoomScale", request)) {
            LOGGER.debug("zoomScale client side changed: " + request.getParameter("zoomScale"));
            streamController.setZoomScale(request.getParameter("zoomScale"));
        }
        if (parameterExists("zoomEnabled", request)) {
            LOGGER.debug("zoomEnabled client side changed: " + request.getParameter("zoomEnabled"));
            streamController.setZoomEnabled(Boolean.valueOf(request.getParameter("zoomEnabled")));
        }
        if (requestUrlContains("actualNodesEdges", request)) {
            int nodes = streamController.actualNumberOfNodesInGraph;
            int edges = streamController.actualNumberOfEdgesInGraph;
            LOGGER.debug(String.format("client updates actual number of nodes (%d) and edges (%d).", nodes, edges));
            byte[] dataInBytes = String.format("{ \"nodes\" : %d, \"edges\" : %d}", nodes, edges).getBytes();
            response.setContentLength(dataInBytes.length);
            IOUtils.write(dataInBytes, response.getOutputStream());
        }
        response.setContentType(getContentType());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private boolean parameterExists(String parameterName, ServletRequest request) {
        String param = request.getParameter(parameterName);
        return param != null && param.trim().length() > 0;
    }

    private boolean requestUrlContains(String requestUrlPart, ServletRequest request) {
        String url = ((HttpServletRequest)request).getRequestURL().toString();
        return url.contains(requestUrlPart);
    }

    private void keepGraphInfo(GraphData graphInfo) {
        if (graphInfo.getNodes() != null) {
            actualNumberOfNodesInGraph = graphInfo.getNodes();
        }
        if (graphInfo.getEdges() != null) {
            actualNumberOfEdgesInGraph = graphInfo.getEdges();
        }
    }

    public String getMaxNodes() {
        return String.valueOf(maxNodes);
    }

    public void setMaxNodes(int maxNodes) {
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

    public int getActualNumberOfEdgesInGraph() {
        return actualNumberOfEdgesInGraph;
    }

    public void setActualNumberOfEdgesInGraph(int actualNumberOfEdgesInGraph) {
        this.actualNumberOfEdgesInGraph = actualNumberOfEdgesInGraph;
    }

    public int getActualNumberOfNodesInGraph() {
        return actualNumberOfNodesInGraph;
    }

    public void setActualNumberOfNodesInGraph(int actualNumberOfNodesInGraph) {
        this.actualNumberOfNodesInGraph = actualNumberOfNodesInGraph;
    }

}
