package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SvgSaxToolTipHandler extends SvgSaxAbstractHandler {

    private final static Logger LOGGER = Logger.getLogger(SvgSaxToolTipHandler.class);

    private Map<String, String> rememberNodes = new HashMap<String, String>();

    private boolean gRootNodeSeen = false;

    public SvgSaxToolTipHandler(ByteArrayOutputStream outputStream) {
        super(outputStream);
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
            rememberNodes.put("current", id);
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
                out.writeCharacters(text.replace('_', ' '));
                out.writeEndElement();
                String nodeId = rememberNodes.get("current");
                rememberNodes.put(nodeId.trim(), text);
            } else {
                out.writeCharacters(ch, start, length);
            }
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Map<String, String> getRememberNodes() {
        return rememberNodes;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

}
