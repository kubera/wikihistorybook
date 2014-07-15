package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.fhnw.business.iwi.wikihistorybook.svg.SvgWikiHistoryBook;
import ch.fhnw.business.iwi.wikihistorybook.webapp.Persistence;

@Component
@Scope("session")
public class SvgGraphCreator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private Persistence persistence;

    private transient Map<String, ByteArrayOutputStream> images;

    public String createSvgStreamAndStoreToSession(int year) {
        String imageName = createImageName(year);
        ByteArrayOutputStream svgStream = getImages().get(imageName);
        if (svgStream == null) {
            svgStream = createSvgGraph(year);
            getImages().put(imageName, svgStream);
        }
        return imageName;
    }

    public ByteArrayOutputStream getSvgStream(String imageName) {
        return getImages().get(imageName);
    }

    public ByteArrayOutputStream createSvgGraph(int year) {
        SvgWikiHistoryBook svgWikiHistoryBook = new SvgWikiHistoryBook(year, persistence.getDBProvider());
        return svgWikiHistoryBook.getSvgStream();
    }

    private String createImageName(int year) {
        return String.format("gen-img_%d.svg", year);
    }

    private Map<String, ByteArrayOutputStream> getImages() {
        if (images == null) {
            images = new HashMap<String, ByteArrayOutputStream>();
        }
        return images;
    }
}
