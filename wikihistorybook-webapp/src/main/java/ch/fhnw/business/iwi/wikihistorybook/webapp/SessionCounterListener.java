package ch.fhnw.business.iwi.wikihistorybook.webapp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionCounterListener implements HttpSessionListener {

    @Autowired
    private Persistence persistence;
    
    private static int totalActiveSessions = 0;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    public static boolean hasActiveSessions() {
        return totalActiveSessions > 0;
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        totalActiveSessions++;
        checkDependencyInjection(arg0.getSession().getServletContext());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        if (!hasActiveSessions()) {
            persistence.disconnect();
        }
    }

    private void checkDependencyInjection(ServletContext servletContext) {
        if (persistence == null) {
            // manual dependency injection
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            AutowireCapableBeanFactory ctx = context.getAutowireCapableBeanFactory();
            ctx.autowireBean(this);
        }
    }

}