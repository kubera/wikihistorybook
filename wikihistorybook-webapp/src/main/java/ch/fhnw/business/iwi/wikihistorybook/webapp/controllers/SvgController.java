package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

@Component
@Scope("session")
public class SvgController {

    @Autowired
    private SvgGraphCreator svgGraphCreator;

    public String getUniqueSvgName() {
        return svgGraphCreator.createSvgStreamAndStoreToSession(0);
    }

}
