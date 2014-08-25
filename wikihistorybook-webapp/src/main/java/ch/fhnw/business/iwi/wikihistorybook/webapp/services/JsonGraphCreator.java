package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.json.JsonWikiHistoryBook;

/**
 * Creates the Json as a singleton service (application scope). 
 * 
 * @author Stefan Wagner
 *
 */
@ManagedBean(name="jsonGraphCreator")
@ApplicationScoped
public class JsonGraphCreator extends AbstractGraphCreator {

    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = Logger.getLogger(JsonGraphCreator.class);

    public String createStreamAndStore(GraphData graphData) {
        String imageName = imageName(graphData.getYear(), graphData.getMaxNodes());
        if (!persistence.fileStreamExists(imageName)) {
            ByteArrayOutputStream jsonStream = createGraphStream(graphData);
            persistence.writeFileStream(imageName, jsonStream);
            LOGGER.debug("json stream created for year: " + graphData.getYear() + " with max nodes: " + graphData.getMaxNodes());
        }
        return imageName;
    }

    public ByteArrayOutputStream createGraphStream(GraphData graphData) {
        JsonWikiHistoryBook jsonWikiHistoryBook = new JsonWikiHistoryBook(graphData, persistence.getDBProvider());
        return jsonWikiHistoryBook.getJsonStream();
    }

    public String imageName(int year, int maxNodes) {
        return String.format("gen-data_%d_%d.json", year, maxNodes);
    }

}
