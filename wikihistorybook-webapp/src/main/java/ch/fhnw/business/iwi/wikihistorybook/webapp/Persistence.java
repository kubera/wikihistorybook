package ch.fhnw.business.iwi.wikihistorybook.webapp;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;

@Component
@Scope("singleton")
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
