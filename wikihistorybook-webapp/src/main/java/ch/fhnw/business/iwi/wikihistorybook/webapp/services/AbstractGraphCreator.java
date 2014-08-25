package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;

/**
 * Common functionality for creating a graph byte array stream.
 * 
 * @author Stefan Wagner
 * 
 */
public abstract class AbstractGraphCreator implements Serializable {

    protected static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{persistence}")
    protected Persistence persistence;

    public abstract String createStreamAndStore(GraphData graphInfo);

    public abstract ByteArrayOutputStream createGraphStream(GraphData graphInfo);

    public abstract String imageName(int year, int maxNodes);

    public ByteArrayOutputStream getStream(GraphData graphInfo) {
        String imageName = createStreamAndStore(graphInfo);
        return getStream(imageName);
    }

    public ByteArrayOutputStream getStream(String imageName) {
        return persistence.readFileStream(imageName);
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

}
