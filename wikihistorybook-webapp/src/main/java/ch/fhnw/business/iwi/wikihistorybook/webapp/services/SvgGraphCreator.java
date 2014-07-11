package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkSVG2;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;
import ch.fhnw.business.iwi.wikihistorybook.webapp.Persistence;

@Component
@Scope("session")
public class SvgGraphCreator {
    
    @Autowired
    private Persistence persistence;
    
    private Map<String, ByteArrayOutputStream> images = new HashMap<String, ByteArrayOutputStream>();

    public String createSvgStreamAndStoreToSession(int year) {
        String imageName = createImageName(year);
        ByteArrayOutputStream svgStream = images.get(imageName);
        if (svgStream == null) {
            svgStream = createSvgGraph(year);
            images.put(imageName, svgStream);
        }
        return imageName;
    }

    public ByteArrayOutputStream getSvgStream(String imageName) {
        return images.get(imageName);
    }
    
    public ByteArrayOutputStream createSvgGraph(int year) {
        GraphFactory graphFactory = null;
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            graphFactory = new GraphFactory(year, persistence.getDBProvider());
            IWikiBookContainer wikiBookContainer = new IWikiBookContainer() {

                @Override
                public void showGraph(Graph graph) {
                    FileSinkSVG2 svg = new FileSinkSVG2();
                    Layout layout = Layouts.newLayoutAlgorithm();
                    Toolkit.computeLayout(graph, layout, 1.0);
                    try {
                        svg.writeAll(graph, os);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void setYearText(String string) {
                }

                @Override
                public int getMaxNodes() {
                    return 1000;
                }
            };
            graphFactory.run(wikiBookContainer);
        } catch (Exception e) {
            if (graphFactory != null) {
                graphFactory.getDB().closeConnection();
            }
        }
        return os;
    }

    private String createImageName(int year) {
        return String.format("gen-img_%d.svg", year);
    }

}
