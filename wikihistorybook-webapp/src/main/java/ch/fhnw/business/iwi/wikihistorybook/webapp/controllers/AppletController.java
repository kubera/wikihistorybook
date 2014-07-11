package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.applet.WikiBookContainer;

@Component
@Scope("session")
public class AppletController implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getAppletClassName(){
	    return WikiBookContainer.class.getName();
	}
}
