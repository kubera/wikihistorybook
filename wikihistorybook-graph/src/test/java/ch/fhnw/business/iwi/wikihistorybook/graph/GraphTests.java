package ch.fhnw.business.iwi.wikihistorybook.graph;

import org.graphstream.graph.Graph;
import org.junit.Test;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;

public class GraphTests {

	@Test
	public void simpleTest() {
		
		GraphFactory graphFactory = new GraphFactory(0);
		graphFactory.run(new IWikiBookContainer() {
			
			@Override
			public void showGraph(Graph graph) {
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
