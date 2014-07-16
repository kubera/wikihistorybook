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
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

public class SvgWikiHistoryBook implements IWikiBookContainer {

    private final static Logger LOGGER = Logger.getLogger(SvgWikiHistoryBook.class.getName());

    private final ByteArrayOutputStream svgStream = new ByteArrayOutputStream();

    public SvgWikiHistoryBook(int year, DBProvider dbProvider) {
        GraphFactory graphFactory = null;
        try {
            graphFactory = new GraphFactory(year, dbProvider);
            graphFactory.run(this);
        } catch (Exception e) {
            if (graphFactory != null) {
                graphFactory.getDB().closeConnection();
            }
        }
    }

    @Override
    public int getMaxNodes() {
        return 1000;
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
