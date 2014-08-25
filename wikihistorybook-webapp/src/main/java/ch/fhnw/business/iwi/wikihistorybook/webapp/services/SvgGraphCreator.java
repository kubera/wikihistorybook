package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.svg.SvgWikiHistoryBook;

/**
 * Creates the SVG as a singleton service (application scope). 
 * 
 * @author Stefan Wagner
 *
 */
@ManagedBean(name="svgGraphCreator")
@ApplicationScoped
public class SvgGraphCreator extends AbstractGraphCreator {

    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = Logger.getLogger(SvgGraphCreator.class);

    public String createStreamAndStore(GraphData graphData) {
        String imageName = imageName(graphData.getYear(), graphData.getMaxNodes());
        if (!persistence.fileStreamExists(imageName)) {
            ByteArrayOutputStream svgStream = createGraphStream(graphData);
            persistence.writeFileStream(imageName, svgStream);
            LOGGER.debug("svg stream created for year: " + graphData.getYear() + " with max nodes: " + graphData.getMaxNodes());
        }
        return imageName;
    }

    public ByteArrayOutputStream createGraphStream(GraphData graphData) {
        SvgWikiHistoryBook svgWikiHistoryBook = new SvgWikiHistoryBook(graphData, persistence.getDBProvider());
        return svgWikiHistoryBook.getSvgStream();
    }

    public String imageName(int year, int maxNodes) {
        return String.format("gen-img_%d_%d.svg", year, maxNodes);
    }

}
