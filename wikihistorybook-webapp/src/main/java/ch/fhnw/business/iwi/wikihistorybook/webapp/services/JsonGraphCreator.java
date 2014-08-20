package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

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

    public String createStreamAndStore(int year, int maxNodes) {
        String imageName = imageName(year, maxNodes);
        if (!persistence.fileStreamExists(imageName)) {
            ByteArrayOutputStream jsonStream = createGraphStream(year, maxNodes);
            persistence.writeFileStream(imageName, jsonStream);
            LOGGER.debug("json stream created for year: " + year + " with max nodes: " + maxNodes);
        }
        return imageName;
    }

    public ByteArrayOutputStream createGraphStream(int year, int maxNodes) {
        JsonWikiHistoryBook jsonWikiHistoryBook = new JsonWikiHistoryBook(year, maxNodes, persistence.getDBProvider());
        return jsonWikiHistoryBook.getJsonStream();
    }

    public String imageName(int year, int maxNodes) {
        return String.format("gen-data_%d_%d.json", year, maxNodes);
    }

}
