package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.fhnw.business.iwi.wikihistorybook.applet.WikiBookContainer;

@ManagedBean
@SessionScoped
public class AppletController implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getAppletClassName(){
	    return WikiBookContainer.class.getName();
	}
}
