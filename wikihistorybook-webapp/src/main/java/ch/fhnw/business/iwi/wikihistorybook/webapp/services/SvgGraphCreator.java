package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.svg.SvgWikiHistoryBook;

/**
 * Creates the SVG as a singleton service (application scope). 
 * 
 * @author Stefan Wagner
 *
 */
@ManagedBean(name="svgGraphCreator")
@ApplicationScoped
public class SvgGraphCreator implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(SvgGraphCreator.class);

    @ManagedProperty(value = "#{persistence}")
    private Persistence persistence;

    public String createSvgStreamAndStoreToSession(int year, int maxNodes) {
        String imageName = imageName(year, maxNodes);
        if (!persistence.fileStreamExists(imageName)) {
            ByteArrayOutputStream svgStream = createSvgGraph(year, maxNodes);
            persistence.writeFileStream(imageName, svgStream);
            LOGGER.debug("svg stream created for year: " + year + " with max nodes: " + maxNodes);
        }
        return imageName;
    }

    public ByteArrayOutputStream getSvgStream(int year, int maxNodes) {
        String imageName = createSvgStreamAndStoreToSession(year, maxNodes);
        return getSvgStream(imageName);
    }

    public ByteArrayOutputStream getSvgStream(String imageName) {
        return persistence.readFileStream(imageName);
    }

    public ByteArrayOutputStream createSvgGraph(int year, int maxNodes) {
        SvgWikiHistoryBook svgWikiHistoryBook = new SvgWikiHistoryBook(year, maxNodes, persistence.getDBProvider());
        return svgWikiHistoryBook.getSvgStream();
    }

    public String imageName(int year, int maxNodes) {
        return String.format("gen-img_%d_%d.svg", year, maxNodes);
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

}
