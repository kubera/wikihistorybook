package ch.fhnw.business.iwi.wikihistorybook.svg;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Simple test to manipulate a wikihistory SVG graph image. 
 * 
 * @author Stefan Wagner
 *
 */
public class SvgManipulatorTest {

    private static final String SIMPLE_SVG_FILE_NAME = "/gen-img_-2000.svg";

    @Test
    public void simpleSvgManipulation() throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        ByteArrayOutputStream source = getSource();
        SvgManipulator manipulator = new SvgManipulator(source);
        ByteArrayOutputStream result = manipulator.manipulate();
        assertThat(result).isNotNull();
        doValidate(result.toByteArray());
    }

    private ByteArrayOutputStream getSource() throws IOException, URISyntaxException {
        File testResource = new File(getClass().getResource(SIMPLE_SVG_FILE_NAME).toURI());
        assertThat(testResource).exists();
        byte[] byteArray = IOUtils.toByteArray(new FileInputStream(testResource));
        doValidate(byteArray);
        ByteArrayOutputStream stream = new ByteArrayOutputStream(byteArray.length);
        stream.write(byteArray, 0, byteArray.length);
        return stream;
    }

    private void doValidate(byte[] byteArray) {
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(new DefaultHandler());
            InputSource source = new InputSource(new ByteArrayInputStream(byteArray));
            parser.parse(source);
        } catch (SAXException e) {
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

}
