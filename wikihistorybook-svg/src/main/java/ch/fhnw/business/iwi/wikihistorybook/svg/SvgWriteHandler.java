package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Manipulates the input SVG graph by
 * <ul>
 * <li>adding tool tips on nodes</li>
 * <li>adding hyperlinks on nodes</li>
 * <li>removes partly the text on nodes</li>
 * <li>adds additional attributes to tag</li>
 * </ul>
 * 
 * @author Stefan Wagner
 * 
 */
public class SvgWriteHandler extends SvgAbstractHandler {

    private final static Logger LOGGER = Logger.getLogger(SvgWriteHandler.class);

    private ByteArrayOutputStream outputStream;
    private XMLStreamWriter out;
    private boolean enteredATag = false;
    private int writeTitles = getProperty("show.text.on.nodes");

    public SvgWriteHandler(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void startDocument() throws SAXException {
        allNodes.computeTop(writeTitles);
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(outputStream, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (FactoryConfigurationError e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            out.writeEndDocument();
            out.close();
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String id = getId(attributes);
        try {
            if (!isTagContainingTitleOfNode(qName) || canWriteText()) {
                out.writeStartElement(qName);
                for (int i = 0; i < attributes.getLength(); i++) {
                    String value = attributes.getValue(i);
                    String name = attributes.getQName(i);
                    out.writeAttribute(name, value);
                }
            }
            if (isStartingTagOfNode(qName, id)) {
                currentNode = allNodes.getSvgNode(id);
                out.writeStartElement("a");
                out.writeAttribute("xlink", "http://www.w3.org/1999/xlink", "href", "http://en.wikipedia.org/wiki/"
                        + currentNode.getTitle());
                out.writeAttribute("target", "_blank");
                enteredATag = true;

                out.writeStartElement("title");
                out.writeCharacters(currentNode.getTitle().replace('_', ' '));
                out.writeEndElement();
            }
            if (isSvgTag(qName)) {
                out.writeAttribute("xmlns", "http://www.w3.org/2000/svg", "xlink", "http://www.w3.org/1999/xlink");
                out.writeAttribute(
                        "style",
                        "display: inline; width: inherit; min-width: inherit; max-width: inherit; height: inherit; min-height: inherit; max-height: inherit;");
                out.writeStartElement("g");
                out.writeAttribute("id", "root");
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if (!isTagContainingTitleOfNode(qName) || canWriteText()) {
                out.writeEndElement();
                if (isGSvgTag(qName) && enteredATag) {
                    out.writeEndElement();
                    enteredATag = false;
                }
            }
            if (isSvgTag(qName)) {
                out.writeEndElement();
            }
            if (isGSvgTag(qName) && isInsideNode()) {
                if (!isTagContainingTitleOfNode(qName) || canWriteText()) {
                    --writeTitles;
                }
                currentNode = null;
            }

        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            if (!(length > 0 && isInsideNode()) || (length > 0 && canWriteText())) {
                if (new String(ch, start, length).trim().length() > 0) {
                    out.writeCharacters(ch, start, length);
                }
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private boolean canWriteText() {
        return writeTitles >= 0 && allNodes.isTop(currentNode);
    }

    private int getProperty(String key) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = SvgWriteHandler.class.getClassLoader().getResourceAsStream("wikihistorybook-svg.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Integer.valueOf(prop.getProperty(key));
    }

}
