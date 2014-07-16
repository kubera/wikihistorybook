package ch.fhnw.business.iwi.wikihistorybook.webapp;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;

public class WikiHistoryBookSystemEventListener implements SystemEventListener {

    private final static Logger LOGGER = Logger.getLogger(WikiHistoryBookSystemEventListener.class.getName());
    
    @Autowired
    private Persistence persistence;

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {

        if (event instanceof PostConstructApplicationEvent) {
            LOGGER.info("Start Wikihistorybook Webapp");
        }

        if (event instanceof PreDestroyApplicationEvent) {
            LOGGER.info("Shutdown Wikihistorybook Webapp");
            getDBProvider().closeConnection();
        }

    }

    @Override
    public boolean isListenerForSource(Object source) {
        return (source instanceof Application);

    }

    private DBProvider getDBProvider() {
        if (persistence == null) {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                    .getContext();
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            AutowireCapableBeanFactory ctx = context.getAutowireCapableBeanFactory();
            ctx.autowireBean(this);
        }
        return persistence.getDBProvider();
    }

}
