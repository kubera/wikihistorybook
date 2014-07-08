package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import javax.faces.bean.ManagedBean;

import ch.fhnw.business.iwi.wikihistorybook.applet.WikiBookContainer;

@ManagedBean
public class AppletController {

	public String getAppletClassName(){
	    return WikiBookContainer.class.getName();
	}
}
