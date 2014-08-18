package ch.fhnw.business.iwi.wikihistorybook.graph;

import static org.fest.assertions.Assertions.assertThat;

import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

/**
 * Simple test to trigger the graph-stream graph generation. 
 * 
 * @author Stefan Wagner
 *
 */
public class GraphTest {

    @Test
    public void simpleTest() {
        GraphFactory graphFactory = new GraphFactory(0, DBProvider.getInstance());
        graphFactory.run(new IWikiBookContainer() {

            @Override
            public void showGraph(Graph graph) {
                assertThat(graph.getNodeSet()).isNotEmpty();
                assertThat(graph.getEdgeSet()).isNotEmpty();
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
    public void spanningTree() {
        Graph graph = new SingleGraph("Spanning Tree: make a tree out of a graph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("AB", "A", "B").setAttribute("weight", 3);
        graph.addEdge("BC", "B", "C").setAttribute("weight", 6);
        graph.addEdge("CD", "C", "D").setAttribute("weight", 3);
        graph.addEdge("DA", "D", "A").setAttribute("weight", 2);
        graph.addEdge("AC", "A", "C").setAttribute("weight", 4);
        graph.addEdge("BD", "B", "D").setAttribute("weight", 1);

        Kruskal kruskal = new Kruskal("ui.class", "intree", "notintree");
        kruskal.setWeightAttribute("weight");
        kruskal.init(graph);
        kruskal.compute();

        for (Edge e : graph.getEdgeSet()) {
            // instead of removing: configure the layout not to show the edge
            if ("notintree".equals(e.getAttribute("ui.class"))) {
                graph.removeEdge(e);
            }
        }
        // just for examination; don't use following code in automated tests:
        // graph.display();
        // try {
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

}
