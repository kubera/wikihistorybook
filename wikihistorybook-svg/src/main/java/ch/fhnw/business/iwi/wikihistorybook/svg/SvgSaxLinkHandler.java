package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SvgSaxLinkHandler extends SvgSaxAbstractHandler {

	private final static Logger LOGGER = Logger
			.getLogger(SvgSaxLinkHandler.class);

	private Map<String, String> rememberedNodes;
	private boolean enteredATag = false;

	public SvgSaxLinkHandler(ByteArrayOutputStream outputStream) {
		super(outputStream);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		String id = attributes.getValue("id");
		if (id != null && rememberedNodes.containsKey(id.trim())) {
			try {
				out.writeStartElement("a");
				out.writeAttribute("xlink", "http://www.w3.org/1999/xlink",
						"href", "http://en.wikipedia.org/wiki/"
								+ rememberedNodes.get(id.trim()));
				out.writeAttribute("target", "_blank");
				enteredATag = true;
			} catch (XMLStreamException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
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
		if ("svg".equals(qName)) {
			try {
				out.writeAttribute("xmlns", "http://www.w3.org/2000/svg",
						"xlink", "http://www.w3.org/1999/xlink");
				out.writeStartElement("g");
				out.writeAttribute("id", "root");
			} catch (XMLStreamException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		try {
			out.writeEndElement();
			if ("g".equals(qName) && enteredATag) {
				out.writeEndElement();
				enteredATag = false;
			}
			if ("svg".equals(qName)) {
				out.writeEndElement();
			}
		} catch (XMLStreamException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		try {
			out.writeCharacters(ch, start, length);
		} catch (XMLStreamException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void setRememberedNodes(Map<String, String> rememberedNodes) {
		this.rememberedNodes = rememberedNodes;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
