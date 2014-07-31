package ch.fhnw.business.iwi.wikihistorybook.webapp;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Countes the current sessions. 
 * 
 * @author Stefan Wagner
 *
 */
public class SessionCounterListener implements HttpSessionListener {

    private static int totalActiveSessions = 0;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    public static boolean hasActiveSessions() {
        return totalActiveSessions > 0;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        totalActiveSessions++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        totalActiveSessions--;
        if (!hasActiveSessions()) {
            Persistence persistence = getPersistence(sessionEvent.getSession());
            if (persistence != null) {
                persistence.disconnect();
            }
        }
    }

    private Persistence getPersistence(HttpSession httpSession) {
        Persistence persistence = (Persistence) httpSession.getServletContext().getAttribute("persistence");
        if (persistence != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && context.getExternalContext() != null) {
                persistence = (Persistence) context.getExternalContext().getApplicationMap().get("persistence");
            }
        }
        return persistence;
    }

}