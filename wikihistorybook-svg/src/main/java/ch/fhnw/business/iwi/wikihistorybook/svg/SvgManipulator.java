package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class SvgManipulator {

    private final static Logger LOGGER = Logger.getLogger(SvgManipulator.class);

    private ByteArrayOutputStream result = new ByteArrayOutputStream();
    private ByteArrayOutputStream source;

    public SvgManipulator(ByteArrayOutputStream source) {
        this.source = source;
    }

    public ByteArrayOutputStream manipulate() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SvgAnalyser analyser = new SvgAnalyser();
        SvgWriteHandler writeHandler = new SvgWriteHandler(result);
        try {
            factory.newSAXParser().parse(new ByteArrayInputStream(source.toByteArray()), analyser);
            writeHandler.setAllNodes(analyser.getAllNodes());
            factory.newSAXParser().parse(new ByteArrayInputStream(source.toByteArray()), writeHandler);
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

}
