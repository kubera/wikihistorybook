package ch.fhnw.business.iwi.wikihistorybook.graph;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkSVG2;
import org.junit.Assert;
import org.junit.Test;

public class GraphTests {

    @Test
    public void simpleTest() {
        final FileSinkSVG2 svg = new FileSinkSVG2();
        GraphFactory graphFactory = new GraphFactory(0);
        graphFactory.run(new IWikiBookContainer() {

            @Override
            public void showGraph(Graph graph) {
                Object attribute = graph.getAttribute("ui.stylesheet");
                assertThat(attribute).isNotNull();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    svg.writeAll(graph, baos);
                    assertThat(baos.size()).isGreaterThan(5);
                } catch (IOException e) {
                    Assert.fail();
                }
                
            }

            @Override
            public void setYearText(String string) {
            }

            @Override
            public int getMaxNodes() {
                return 100;
            }

        });

    }

}
