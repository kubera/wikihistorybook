package ch.fhnw.business.iwi.wikihistorybook.webapp.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import ch.fhnw.business.iwi.wikihistorybook.webapp.services.SvgGraphCreator;

@ManagedBean
@SessionScoped
public class SvgController implements Serializable {

    private static final long serialVersionUID = 1L;

    private int year = 0;
    private int maxNodes = 1000;

    @ManagedProperty("#{svgGraphCreator}")
    private SvgGraphCreator svgGraphCreator;

    public String getUniqueSvgName() {
        return svgGraphCreator.imageName(year, maxNodes);
    }

    public String getSvgStream() {
        return svgGraphCreator.getSvgStream(year, maxNodes).toString();
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    public String getMaxNodes() {
        return String.valueOf(maxNodes);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSvgGraphCreator(SvgGraphCreator svgGraphCreator) {
        this.svgGraphCreator = svgGraphCreator;
    }

}
