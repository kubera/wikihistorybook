package ch.fhnw.business.iwi.wikihistorybook.applet;

import ch.fhnw.business.iwi.wikihistorybook.graph.GraphFactory;
import ch.fhnw.business.iwi.wikihistorybook.graph.IWikiBookContainer;

/**
 * Encapsulating the necessary threading part. 
 * 
 * @author Stefan Wagner
 *
 */
public class GraphThread extends Thread {

	private GraphFactory graphFactory;
	private IWikiBookContainer wikiBookContainer;

	public GraphThread(IWikiBookContainer wikiBookContainer,
			GraphFactory graphFactory) {
		this.wikiBookContainer = wikiBookContainer;
		this.graphFactory = graphFactory;
	}

	@Override
	public void run() {
		graphFactory.run(wikiBookContainer);
	}

}
