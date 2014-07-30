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

public class SvgWriterHandler extends SvgHandler {

    private final static Logger LOGGER = Logger.getLogger(SvgWriterHandler.class);

    protected ByteArrayOutputStream outputStream;
    protected XMLStreamWriter out;

    public SvgWriterHandler(int showNumberOfTitles, ByteArrayOutputStream outputStream) {
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
            if (isSvgTag(qName)) {
                out.writeEndElement();
            }
            if (!isTagContainingTitleOfNode(qName) && !isSvgTag(qName)) {
                out.writeEndElement();
                if (isGSvgTag(qName) && isInsideNode()) {
                    out.writeEndElement();
                }
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (isGSvgTag(qName) && isInsideNode()) {
            currentNode = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            if (length > 0 && isInsideNode()) {
                out.writeStartElement("title");
                out.writeCharacters(currentNode.getTitle().replace('_', ' '));
                out.writeEndElement();
            } else {
                out.writeCharacters(ch, start, length);
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
