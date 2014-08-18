package ch.fhnw.business.iwi.wikihistorybook.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

/**
 * Creates the graph by year and provides a json byte stream.
 * 
 * @author Stefan Wagner
 */
public class JsonWikiHistoryBook implements IWikiBookContainer {

    private final static Logger LOGGER = Logger.getLogger(JsonWikiHistoryBook.class);

    private final ByteArrayOutputStream svgStream = new ByteArrayOutputStream();

    private int maxNodes;

    public JsonWikiHistoryBook(int year, int maxNodes, DBProvider dbProvider) {
        this.maxNodes = maxNodes;
        GraphFactory graphFactory = null;
        try {
            graphFactory = new GraphFactory(year, dbProvider);
            graphFactory.run(this);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (graphFactory != null) {
                graphFactory.getDB().closeConnection();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getMaxNodes() {
        return maxNodes;
    }

    @Override
    public void setYearText(String string) {
    }

    @Override
    public void showGraph(Graph graph) {
        FileSinkD3Json d3Json = new FileSinkD3Json();
        Layout layout = Layouts.newLayoutAlgorithm();
        Toolkit.computeLayout(graph, layout, 1.0);
        try {
            d3Json.writeAll(graph, svgStream);
        } catch (IOException e) {
            LOGGER.info("failed", e);
        }
    }

    public ByteArrayOutputStream getJsonStream() {
        return svgStream;
    }
}
