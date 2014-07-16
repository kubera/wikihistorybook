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
import org.xml.sax.helpers.DefaultHandler;

public class SvgSaxDefaultHandler extends DefaultHandler {

    private final static Logger LOGGER = Logger.getLogger(SvgSaxDefaultHandler.class);

    private ByteArrayOutputStream outputStream;
    private XMLStreamWriter out;

    private boolean gRootNodeSeen = false;

    public SvgSaxDefaultHandler(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(outputStream, "utf-8"));
            out.writeStartDocument("UTF-8", "1.0");
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
        String id = attributes.getValue("id");
        if (!(("g".equals(qName) || "text".equals(qName)) && gRootNodeSeen)) {
            try {
                out.writeStartElement(qName);
            } catch (XMLStreamException e) {
                LOGGER.error(e.getMessage(), e);
            }
            for (int i = 0; i < attributes.getLength(); i++) {
                String value = attributes.getValue(i);
                String name = attributes.getQName(i);
                try {
                    out.writeAttribute(name, value);
                } catch (XMLStreamException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        if ("g".equals(qName) && id != null && id.startsWith("node-")) {
            gRootNodeSeen = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!(("g".equals(qName) || "text".equals(qName)) && gRootNodeSeen)) {
            try {
                out.writeEndElement();
            } catch (XMLStreamException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        if ("g".equals(qName) && gRootNodeSeen) {
            gRootNodeSeen = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        boolean addTooltip = text.trim().length() > 0 && gRootNodeSeen;
        try {
            if (addTooltip) {
                out.writeStartElement("title");
            }
            out.writeCharacters(ch, start, length);
            if (addTooltip) {
                out.writeEndElement();
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
