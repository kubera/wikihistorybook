package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

/**
 * Common functionality for creating a graph byte array stream.
 * 
 * @author Stefan Wagner
 * 
 */
abstract class AbstractGraphCreator implements Serializable {

    protected static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{persistence}")
    protected Persistence persistence;

    public abstract String createStreamAndStore(int year, int maxNodes);

    public abstract ByteArrayOutputStream createGraphStream(int year, int maxNodes);

    public abstract String imageName(int year, int maxNodes);

    public ByteArrayOutputStream getStream(int year, int maxNodes) {
        String imageName = createStreamAndStore(year, maxNodes);
        return getStream(imageName);
    }

    public ByteArrayOutputStream getStream(String imageName) {
        return persistence.readFileStream(imageName);
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

}
