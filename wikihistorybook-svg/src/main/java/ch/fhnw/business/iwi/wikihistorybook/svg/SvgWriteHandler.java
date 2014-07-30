package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SvgWriteHandler extends SvgHandler {

    private final static Logger LOGGER = Logger.getLogger(SvgWriteHandler.class);

    private ByteArrayOutputStream outputStream;
    private XMLStreamWriter out;
    private boolean enteredATag = false;
    private int writeTitles = 1;

    public SvgWriteHandler(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void startDocument() throws SAXException {
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
            if (!isTagContainingTitleOfNode(qName)) {
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
            if (!isTagContainingTitleOfNode(qName)) {
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
                currentNode = null;
                --writeTitles;
            }

        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            if (!(length > 0 && isInsideNode())) {
                out.writeCharacters(ch, start, length);
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
