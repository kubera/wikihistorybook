package ch.fhnw.business.iwi.wikihistorybook.webapp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounterListener implements HttpSessionListener {

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
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        if (!hasActiveSessions()) {
            getPersistence(arg0.getSession().getServletContext()).disconnect();
        }
    }

    private Persistence getPersistence(ServletContext servletContext) {
        return (Persistence) servletContext.getAttribute("persistence");
    }

}