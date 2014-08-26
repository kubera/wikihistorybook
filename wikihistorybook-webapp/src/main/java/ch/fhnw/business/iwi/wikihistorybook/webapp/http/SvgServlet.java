package ch.fhnw.business.iwi.wikihistorybook.webapp.http;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.fhnw.business.iwi.wikihistorybook.webapp.controllers.SvgController;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphStreamCreator;

/**
 * Servlet for the SVG request.
 * 
 * @author Stefan Wagner
 * 
 */
public class SvgServlet extends AbstractStreamServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected SvgGraphStreamCreator getGraphCreator() {
        Object bean = getServletContext().getAttribute("svgGraphStreamCreator");
        if (bean == null) {
            bean = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("svgGraphCreator");
        }
        return (SvgGraphStreamCreator) bean;
    }

    @Override
    protected String getContentType() {
        return "image/svg+xml ";
    }

    @Override
    protected SvgController getStreamController(HttpServletRequest request) {
        return (SvgController) request.getSession().getAttribute("svgController");
    }

}
