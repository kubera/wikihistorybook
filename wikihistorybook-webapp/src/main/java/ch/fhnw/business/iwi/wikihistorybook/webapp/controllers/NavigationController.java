package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.fhnw.business.iwi.wikihistorybook.webapp.SessionCounterListener;

@ManagedBean
@ApplicationScoped
public class NavigationController implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer getSessionCount() {
        return SessionCounterListener.getTotalActiveSession();
    }
}
