package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;
import ch.fhnw.business.iwi.wikihistorybook.json.JsonWikiHistoryBook;

/**
 * Creates the Json as a singleton service (application scope).
 * 
 * @author Stefan Wagner
 * 
 */
@ManagedBean
@ApplicationScoped
public class JsonGraphStreamCreator extends AbstractGraphStreamCreator {

    private static final long serialVersionUID = 1L;

    @Override
    protected ByteArrayOutputStream createGraphStream(GraphData graphData) {
        JsonWikiHistoryBook jsonWikiHistoryBook = new JsonWikiHistoryBook(graphData, persistence.getDBProvider());
        return jsonWikiHistoryBook.getJsonStream();
    }

    @Override
    protected String imageName(int year, int maxNodes) {
        return String.format("gen-data_%d_%d.json", year, maxNodes);
    }

}
