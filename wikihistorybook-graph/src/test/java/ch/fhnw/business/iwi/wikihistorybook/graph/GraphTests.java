package ch.fhnw.business.iwi.wikihistorybook.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSinkSVG2;
import org.junit.Test;

public class GraphTests {

	@Test
	public void simpleTest() {
		final FileSinkSVG2 svg = new FileSinkSVG2();
		GraphFactory graphFactory = new GraphFactory(0);
		graphFactory.run(new IWikiBookContainer() {
			
			@Override
			public void showGraph(Graph graph) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(File.createTempFile("test", ".svg"));
					svg.writeAll(graph, fos);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	@Test
	public void graphStreamSimpleExample() throws FileNotFoundException, IOException {
		DefaultGraph g = new DefaultGraph("my beautiful graph");
//		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
		FileSinkSVG2 svg = new FileSinkSVG2();
//		svg.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		 
		g.addNode("A");
		g.addNode("B");
		g.addNode("C");
		 
		g.addEdge("AB", "A", "B");
		g.addEdge("AC", "A", "C");
		g.addEdge("BC", "B", "C");
		 
		FileOutputStream fos = new FileOutputStream(File.createTempFile("test", ".svg"));

		svg.writeAll(g, fos);
	}
}
