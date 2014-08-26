package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.svg.SvgWikiHistoryBook;

/**
 * Creates the SVG as a singleton service (application scope). 
 * 
 * @author Stefan Wagner
 *
 */
@ManagedBean
@ApplicationScoped
public class SvgGraphStreamCreator extends AbstractGraphStreamCreator {

    private static final long serialVersionUID = 1L;

    @Override
    protected ByteArrayOutputStream createGraphStream(GraphData graphData) {
        SvgWikiHistoryBook svgWikiHistoryBook = new SvgWikiHistoryBook(graphData, persistence.getDBProvider());
        return svgWikiHistoryBook.getSvgStream();
    }

    @Override
    protected String imageName(int year, int maxNodes) {
        return String.format("gen-img_%d_%d.svg", year, maxNodes);
    }

}
