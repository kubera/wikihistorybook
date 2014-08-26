package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.JsonGraphStreamCreator;

@ManagedBean
@SessionScoped
@WebFilter(urlPatterns = { "/json-d3js/maxNodes", "/json-d3js/zoomScale", "/json-d3js/zoomEnabled",
        "/json-d3js/actualNodesEdges" })
public class JsonController extends AbstractStreamController implements Filter, Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{jsonGraphStreamCreator}")
    private JsonGraphStreamCreator jsonGraphCreator;

    @Override
    protected JsonGraphStreamCreator getGraphStreamCreator() {
        return jsonGraphCreator;
    }

    @Override
    protected JsonController getStreamController(HttpSession session) {
        return (JsonController) session.getAttribute("jsonController");
    }

    @Override
    protected String getContentType() {
        return "application/json ";
    }

    public void setJsonGraphCreator(JsonGraphStreamCreator jsonGraphCreator) {
        this.jsonGraphCreator = jsonGraphCreator;
    }

}
