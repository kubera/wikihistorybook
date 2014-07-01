package ch.fhnw.business.iwi.wikihistorybook.graph;

import org.graphstream.graph.Graph;

public interface IWikiBookContainer {

	int getMaxNodes();

	void setYearText(String string);

	void showGraph(Graph graph);

}
