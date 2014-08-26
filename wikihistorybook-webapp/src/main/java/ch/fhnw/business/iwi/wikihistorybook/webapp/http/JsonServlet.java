package ch.fhnw.business.iwi.wikihistorybook.webapp.http;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.fhnw.business.iwi.wikihistorybook.webapp.controllers.JsonController;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.JsonGraphCreator;

/**
 * Servlet for the Json requests.
 * 
 * @author Stefan Wagner
 * 
 */
public class JsonServlet extends AbstractStreamServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected JsonGraphCreator getGraphCreator() {
        Object bean = getServletContext().getAttribute("jsonGraphCreator");
        if (bean == null) {
            bean = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("jsonGraphCreator");
        }
        return (JsonGraphCreator) bean;
    }

    @Override
    protected String getContentType() {
        return "application/json ";
    }

    @Override
    protected JsonController getStreamController(HttpServletRequest request) {
        return (JsonController) request.getSession().getAttribute("jsonController");
    }

}
