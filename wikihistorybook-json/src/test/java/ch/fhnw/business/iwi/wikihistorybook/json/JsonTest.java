package ch.fhnw.business.iwi.wikihistorybook.json;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;

/**
 * Simple test to check json generation.
 * 
 * @author Stefan Wagner
 */
public class JsonTest {

    @Test
    public void generateJson() {
        JsonWikiHistoryBook test = new JsonWikiHistoryBook(new GraphData(-1000, 1000), DBProvider.getInstance());
        ByteArrayOutputStream stream = test.getJsonStream();
//        writeTmpFile(stream);
        assertThat(stream.size()).isGreaterThan(0);
    }

    @SuppressWarnings("unused")
    private void writeTmpFile(ByteArrayOutputStream outputStream) throws FileNotFoundException, IOException {
        byte[] byteStream = outputStream.toByteArray();
        FileOutputStream tmpFile = new FileOutputStream(File.createTempFile("wikihistorybook-json-", ".json"));
        tmpFile.write(byteStream, 0, byteStream.length);
        tmpFile.flush();
        tmpFile.close();
    }

}
