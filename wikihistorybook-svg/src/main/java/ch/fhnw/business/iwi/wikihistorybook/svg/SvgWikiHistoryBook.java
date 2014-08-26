package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkSVG2;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

/**
 * Main class to generate the SVG image of a certain year of the
 * wikihistorybook.
 * 
 * @author Stefan Wagner
 * 
 */
public class SvgWikiHistoryBook implements IWikiBookContainer {

    private final static Logger LOGGER = Logger.getLogger(SvgWikiHistoryBook.class);

    private final ByteArrayOutputStream svgStream = new ByteArrayOutputStream();

    private GraphData graphData;

    public SvgWikiHistoryBook(GraphData graphData, DBProvider dbProvider) {
        this.graphData = graphData;
        GraphFactory graphFactory = null;
        try {
            graphFactory = new GraphFactory(graphData.getYear(), dbProvider);
            graphFactory.run(this);
        } catch (Exception e) {
            if (graphFactory != null) {
                graphFactory.getDB().closeConnection();
            }
        }
    }

    @Override
    public int getMaxNodes() {
        return graphData.getMaxNodes();
    }

    @Override
    public void setYearText(String string) {
    }

    @Override
    public void showGraph(Graph graph) {
        FileSinkSVG2 svg = new FileSinkSVG2();
        Layout layout = Layouts.newLayoutAlgorithm();
        Toolkit.computeLayout(graph, layout, 1.0);
        try {
            graphData.setNodes(graph.getNodeCount());
            graphData.setEdges(graph.getEdgeCount());
            svg.writeAll(graph, svgStream);
        } catch (IOException e) {
            LOGGER.debug("failed", e);
        }
    }

    public ByteArrayOutputStream getSvgStream() {
        SvgManipulator manipulator = new SvgManipulator(svgStream);
        return manipulator.manipulate();
    }
}
