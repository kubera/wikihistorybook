package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;

/**
 * Common functionality for creating a graph byte array stream.
 * 
 * @author Stefan Wagner
 * 
 */
public abstract class AbstractGraphStreamCreator implements GraphStreamCreator, Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = Logger.getLogger(AbstractGraphStreamCreator.class);

    @ManagedProperty(value = "#{persistence}")
    protected Persistence persistence;

    protected abstract ByteArrayOutputStream createGraphStream(GraphData graphInfo);

    protected abstract String imageName(int year, int maxNodes);

    @Override
    public ByteArrayOutputStream getGraphStream(GraphData graphData) {
        String imageName = imageName(graphData.getYear(), graphData.getMaxNodes());
        ByteArrayOutputStream stream;
        if (!persistence.fileStreamExists(imageName)) {
            stream = createAndStoreGraphStream(graphData);
        } else {
            stream = persistence.readFileStream(imageName);
        }
        return stream;
    }

    @Override
    public ByteArrayOutputStream getGraphStream(String graphName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (persistence.fileStreamExists(graphName)) {
            stream = persistence.readFileStream(graphName);
        }
        return stream;
    }

    @Override
    public String getGraphName(GraphData graphData) {
        String imageName = imageName(graphData.getYear(), graphData.getMaxNodes());
        if (!persistence.fileStreamExists(imageName)) {
            createAndStoreGraphStream(graphData);
        }
        return imageName;
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    private ByteArrayOutputStream createAndStoreGraphStream(GraphData graphData) {
        String imageName = imageName(graphData.getYear(), graphData.getMaxNodes());
        ByteArrayOutputStream stream = createGraphStream(graphData);
        persistence.writeFileStream(imageName, stream);
        LOGGER.debug(String.format("stream created for year: %d with max nodes: %d", graphData.getYear(),
                graphData.getMaxNodes()));
        return stream;
    }
}
