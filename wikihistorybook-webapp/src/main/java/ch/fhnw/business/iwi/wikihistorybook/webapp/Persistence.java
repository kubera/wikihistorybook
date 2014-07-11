package ch.fhnw.business.iwi.wikihistorybook.webapp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;

@Component
@Scope("singleton")
public class Persistence {

    private DBProvider dbProvider;

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
