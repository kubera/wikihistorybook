package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphStreamCreator;

@ManagedBean
@SessionScoped
@WebFilter(urlPatterns = { "/svg/maxNodes", "/svg/zoomScale", "/svg/zoomEnabled", "/svg/actualNodesEdges" })
public class SvgController extends AbstractStreamController implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{svgGraphStreamCreator}")
    private SvgGraphStreamCreator svgGraphCreator;

    @Override
    protected SvgGraphStreamCreator getGraphStreamCreator() {
        return svgGraphCreator;
    }

    @Override
    protected SvgController getStreamController(HttpSession session) {
        return (SvgController) session.getAttribute("svgController");
    }

    @Override
    protected String getContentType() {
        return "text/plain";
    }

    public void setSvgGraphCreator(SvgGraphStreamCreator svgGraphCreator) {
        this.svgGraphCreator = svgGraphCreator;
    }

}
