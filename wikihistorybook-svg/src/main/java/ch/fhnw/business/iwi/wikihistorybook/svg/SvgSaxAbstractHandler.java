package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SvgSaxAbstractHandler extends DefaultHandler {

    protected ByteArrayOutputStream outputStream;
    protected XMLStreamWriter out;

    public SvgSaxAbstractHandler(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    protected abstract Logger getLogger();

    @Override
    public void startDocument() throws SAXException {
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(outputStream, "utf-8"));
//            out.writeStartDocument("UTF-8", "1.0");
        } catch (UnsupportedEncodingException e) {
            getLogger().error(e.getMessage(), e);
        } catch (XMLStreamException e) {
            getLogger().error(e.getMessage(), e);
        } catch (FactoryConfigurationError e) {
            getLogger().error(e.getMessage(), e);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            out.writeEndDocument();
            out.close();
        } catch (XMLStreamException e) {
            getLogger().error(e.getMessage(), e);
        }
    }

}
