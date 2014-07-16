package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

@Component
@Scope("session")
public class SvgController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private SvgGraphCreator svgGraphCreator;

    public String getUniqueSvgName() {
        return svgGraphCreator.createSvgStreamAndStoreToSession(0);
    }

    public String getSvgStream() {
        String name = getUniqueSvgName();
        return svgGraphCreator.getSvgStream(name).toString();
    }
}
