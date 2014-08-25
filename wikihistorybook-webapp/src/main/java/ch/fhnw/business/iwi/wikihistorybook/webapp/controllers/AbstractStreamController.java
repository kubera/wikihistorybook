package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.ByteArrayOutputStream;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.webapp.services.AbstractGraphCreator;

public abstract class AbstractStreamController {

    protected int year = 0;
    protected int maxNodes = 1000;
    protected String zoomScale = "0.2";
    protected boolean zoomEnabled = false;
    protected int actualNumberOfNodesInGraph;
    protected int actualNumberOfEdgesInGraph;

    protected abstract AbstractGraphCreator getGraphCreator();

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

    private void keepGraphInfo(GraphData graphInfo) {
        if (graphInfo.getNodes() != null) {
            actualNumberOfNodesInGraph = graphInfo.getNodes();
        }
        if (graphInfo.getEdges() != null) {
            actualNumberOfEdgesInGraph = graphInfo.getEdges();
        }
    }

}
