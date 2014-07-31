package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.svg.SvgWikiHistoryBook;
import ch.fhnw.business.iwi.wikihistorybook.webapp.Persistence;

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

     private transient Map<String, ByteArrayOutputStream> images;

    public String createSvgStreamAndStoreToSession(int year, int maxNodes) {
        String imageName = imageName(year, maxNodes);
        ByteArrayOutputStream svgStream = getImages().get(imageName);
        if (svgStream == null) {
            svgStream = createSvgGraph(year, maxNodes);
            getImages().put(imageName, svgStream);
            LOGGER.debug("svg stream created for year: " + year + " with max nodes: " + maxNodes);
        }
        return imageName;
    }

    public ByteArrayOutputStream getSvgStream(int year, int maxNodes) {
        String imageName = createSvgStreamAndStoreToSession(year, maxNodes);
        return getSvgStream(imageName);
    }

    public ByteArrayOutputStream getSvgStream(String imageName) {
        return getImages().get(imageName);
    }

    public ByteArrayOutputStream createSvgGraph(int year, int maxNodes) {
        SvgWikiHistoryBook svgWikiHistoryBook = new SvgWikiHistoryBook(year, maxNodes, persistence.getDBProvider());
        return svgWikiHistoryBook.getSvgStream();
    }

    public String imageName(int year, int maxNodes) {
        return String.format("gen-img_%d_%d.svg", year, maxNodes);
    }

    private Map<String, ByteArrayOutputStream> getImages() {
        if (images == null) {
            images = new HashMap<String, ByteArrayOutputStream>();
        }
        return images;
    }
    
    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

}
