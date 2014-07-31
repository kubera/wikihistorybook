package ch.fhnw.business.iwi.wikihistorybook.webapp;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;

/**
 * Encapsulates the persistence singleton instance. 
 * 
 * @author Stefan Wagner
 *
 */
@ManagedBean
@ApplicationScoped
public class Persistence implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient DBProvider dbProvider;

    public DBProvider getDBProvider() {
        if (dbProvider == null) {
            dbProvider = DBProvider.getInstance();
        }
        return dbProvider;
    }

    public void disconnect() {
        getDBProvider().closeConnection();
    }
}
