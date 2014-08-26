package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;

public interface GraphStreamCreator {

    public ByteArrayOutputStream getGraphStream(GraphData graphData);

    public ByteArrayOutputStream getGraphStream(String graphName, GraphData graphData);

    public String getGraphName(GraphData graphData);

}
