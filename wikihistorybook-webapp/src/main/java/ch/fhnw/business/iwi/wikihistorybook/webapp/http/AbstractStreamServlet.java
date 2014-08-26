package ch.fhnw.business.iwi.wikihistorybook.webapp.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.webapp.controllers.AbstractStreamController;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.AbstractGraphCreator;

public abstract class AbstractStreamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(AbstractStreamServlet.class);

    protected abstract AbstractGraphCreator getGraphCreator();
    protected abstract String getContentType();
    protected abstract AbstractStreamController getStreamController(HttpServletRequest request);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonName = request.getPathInfo().substring(1);
        ByteArrayOutputStream jsonStream = getGraphCreator().getStream(jsonName);
        setJsonStream2Response(jsonStream, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int year = Integer.valueOf(request.getParameter("year"));
        int maxNodes = Integer.valueOf(request.getParameter("maxNodes"));
        LOGGER.debug(String.format("receiving graph input: year %d, maxNodes %d", year, maxNodes));
        GraphData graphData = new GraphData(year, maxNodes);
        String imageName = getGraphCreator().createStreamAndStore(graphData);
        setValues2Session(graphData, request);
        setImageName2Response(imageName, response);
    }

    private void setJsonStream2Response(ByteArrayOutputStream imageContent, HttpServletResponse response)
            throws IOException {
        response.reset();
        response.setContentType(getContentType());
        response.setContentLength(imageContent.size());
        IOUtils.write(imageContent.toByteArray(), response.getOutputStream());
    }

    private void setImageName2Response(String jsonName, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType(getContentType());
        byte[] nameInBytes = jsonName.getBytes("UTF-8");
        response.setContentLength(nameInBytes.length);
        IOUtils.write(nameInBytes, response.getOutputStream());
    }

    private void setValues2Session(GraphData graphData, HttpServletRequest request) {
        AbstractStreamController controller = getStreamController(request);
        controller.setYear(graphData.getYear());
        if (graphData.getNodes() != null) {
            controller.setActualNumberOfNodesInGraph(graphData.getNodes());
        }
        if (graphData.getEdges() != null) {
            controller.setActualNumberOfEdgesInGraph(graphData.getEdges());
        }
    }

}
