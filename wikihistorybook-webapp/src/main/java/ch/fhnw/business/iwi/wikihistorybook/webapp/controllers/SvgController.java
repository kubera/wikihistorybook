package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkSVG2;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

@ManagedBean
@RequestScoped
public class SvgController {

    public String getUniqueSvgName() {
        return createUniqueSvgGraph();
    }

    private String createUniqueSvgGraph() {
        String uniqueSvgName = UUID.randomUUID().toString();
        // create svg graph
        ByteArrayOutputStream svgContent = createSvgGraph();
        // add to session
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().put(uniqueSvgName, svgContent);

        return uniqueSvgName;
    }

    private ByteArrayOutputStream createSvgGraph() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        GraphFactory graphFactory = new GraphFactory(-1111);
        IWikiBookContainer wikiBookContainer = new IWikiBookContainer() {

            @Override
            public void showGraph(Graph graph) {
                FileSinkSVG2 svg = new FileSinkSVG2();
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
        return os;
    }

}
