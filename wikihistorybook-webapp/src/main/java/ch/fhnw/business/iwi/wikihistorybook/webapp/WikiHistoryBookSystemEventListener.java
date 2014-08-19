package ch.fhnw.business.iwi.wikihistorybook.webapp;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.Persistence;

/**
 * The applications start and stop events. 
 * 
 * @author Stefan Wagner
 *
 */
public class WikiHistoryBookSystemEventListener implements SystemEventListener {

    private final static Logger LOGGER = Logger.getLogger(WikiHistoryBookSystemEventListener.class.getName());

    private Persistence persistence;

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {

        if (event instanceof PostConstructApplicationEvent) {
            LOGGER.info("Start Wikihistorybook Webapp");
        }

        if (event instanceof PreDestroyApplicationEvent) {
            LOGGER.info("Shutdown Wikihistorybook Webapp");
            getPersistence().getDBProvider().closeConnection();
        }

    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof Application);

    }

    private Persistence getPersistence() {
        if (persistence == null) {
            persistence = (Persistence) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap()
                    .get("persistence");
        }
        return persistence;
    }

}
