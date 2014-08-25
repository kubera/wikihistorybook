package ch.fhnw.business.iwi.wikihistorybook.webapp.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.webapp.controllers.JsonController;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.JsonGraphCreator;

/**
 * Servlet for the Json requests. 
 * 
 * @author Stefan Wagner
 *
 */
public class JsonServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(JsonServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonName = request.getPathInfo().substring(1);
        ByteArrayOutputStream jsonStream = getJsonGraphCreator().getStream(jsonName);
        setJsonStream2Response(jsonStream, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int year = Integer.valueOf(request.getParameter("year"));
        int maxNodes = Integer.valueOf(request.getParameter("maxNodes"));
        LOGGER.debug(String.format("receiving json data values: year %d, maxNodes %d", year, maxNodes));
        setValues2Session(year, request);
        String imageName = getJsonGraphCreator().createStreamAndStore(new GraphData(year, maxNodes));
        setImageName2Response(imageName, response);
    }

    private void setJsonStream2Response(ByteArrayOutputStream imageContent, HttpServletResponse response)
            throws IOException {
        response.reset();
        response.setContentType("application/json ");
        response.setContentLength(imageContent.size());
        IOUtils.write(imageContent.toByteArray(), response.getOutputStream());
    }

    private void setImageName2Response(String jsonName, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/json ");
        byte[] nameInBytes = jsonName.getBytes("UTF-8");
        response.setContentLength(nameInBytes.length);
        IOUtils.write(nameInBytes, response.getOutputStream());
    }

    private void setValues2Session(int year, HttpServletRequest request) {
        JsonController controller = (JsonController) request.getSession().getAttribute("jsonController");
        controller.setYear(year);
    }

    private JsonGraphCreator getJsonGraphCreator() {
        Object bean = getServletContext().getAttribute("jsonGraphCreator");
        if (bean == null) {
            bean = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("jsonGraphCreator");
        }
        return (JsonGraphCreator) bean;
    }

}
