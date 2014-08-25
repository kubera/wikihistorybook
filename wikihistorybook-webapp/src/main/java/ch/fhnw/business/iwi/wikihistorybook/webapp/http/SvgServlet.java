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
import ch.fhnw.business.iwi.wikihistorybook.webapp.controllers.SvgController;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

/**
 * Servlet for the SVG request.
 * 
 * @author Stefan Wagner
 * 
 */
public class SvgServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(SvgServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getPathInfo().substring(1);
        ByteArrayOutputStream imageStream = getSvgGraphCreator().getStream(imageName);
        setImageStream2Response(imageStream, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int year = Integer.valueOf(request.getParameter("year"));
        int maxNodes = Integer.valueOf(request.getParameter("maxNodes"));
        LOGGER.debug(String.format("receiving svg image values: year %d, maxNodes %d", year, maxNodes));
        setValues2Session(year, request);
        String imageName = getSvgGraphCreator().createStreamAndStore(new GraphData(year, maxNodes));
        setImageName2Response(imageName, response);
    }

    private void setImageStream2Response(ByteArrayOutputStream imageContent, HttpServletResponse response)
            throws IOException {
        response.reset();
        response.setContentType("image/svg+xml ");
        response.setContentLength(imageContent.size());
        IOUtils.write(imageContent.toByteArray(), response.getOutputStream());
    }

    private void setImageName2Response(String svgName, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("image/svg+xml ");
        byte[] nameInBytes = svgName.getBytes("UTF-8");
        response.setContentLength(nameInBytes.length);
        IOUtils.write(nameInBytes, response.getOutputStream());
    }

    private void setValues2Session(int year, HttpServletRequest request) {
        SvgController controller = (SvgController) request.getSession().getAttribute("svgController");
        controller.setYear(year);
    }

    private SvgGraphCreator getSvgGraphCreator() {
        Object bean = getServletContext().getAttribute("svgGraphCreator");
        if (bean == null) {
            bean = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("svgGraphCreator");
        }
        return (SvgGraphCreator) bean;
    }

}
