package ch.fhnw.business.iwi.wikihistorybook.graph;

import org.graphstream.graph.Graph;

/**
 * Simple interface to decouple the java swing components. 
 * 
 * @author Stefan Wagner
 *
 */
public interface IWikiBookContainer {

	int getMaxNodes();

	void setYearText(String string);

	void showGraph(Graph graph);

}
