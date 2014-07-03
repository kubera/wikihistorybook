package ch.fhnw.business.iwi.wikihistorybook.webapp;

import javax.inject.Named;

import ch.fhnw.business.iwi.wikihistorybook.applet.WikiBookContainer;

@Named
public class AppletBean {

	public String getAppletClassName(){
	    return WikiBookContainer.class.getName();
	}
}
