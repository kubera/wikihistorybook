package ch.fhnw.business.iwi.wikihistorybook.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkBase;

public class FileSinkD3Json extends FileSinkBase {

    private final static Logger LOGGER = Logger.getLogger(FileSinkD3Json.class.getName());
    private final static int EDGES_2_NODES_MULTIPLICATOR = 4;

    protected void outputEndOfFile() throws IOException {
        print("]}");
    }

    protected void outputHeader() throws IOException {
        print("{\"nodes\":[");
    }

    private void print(String format, Object... args) throws IOException {
        output.write(String.format(format, args));
    }

    @Override
    protected void exportGraph(Graph graph) {
        try {
            Map<String, Integer> id2ArrayIndexMapping = new HashMap<String, Integer>();
            TreeSet<Double> allWeights = new TreeSet<Double>();
            List<Double> allEdgeWeights = new ArrayList<Double>();

            prepareData(graph, id2ArrayIndexMapping, allWeights, allEdgeWeights);
            int nodesAdded = addNodes2Stream(graph, allWeights);
            print("],\"links\":[");
            int edgesAdded = addEdges2Stream(graph, id2ArrayIndexMapping, allEdgeWeights, nodesAdded);
            LOGGER.debug("nodes: " + nodesAdded);
            LOGGER.debug("edges: " + edgesAdded);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private void prepareData(Graph graph, Map<String, Integer> id2ArrayIndexMapping, TreeSet<Double> allWeights,
            List<Double> allEdgeWeights) {
        int i = 0;
        for (Node n : graph.getEachNode()) {
            String id = n.getId();
            if (id != null && n.hasAttribute("layout.weight")) {
                Double weight = n.getAttribute("layout.weight");
                id2ArrayIndexMapping.put(id, i++);
                allWeights.add(weight);
                for (Edge e : n.getEdgeSet()) {
                    if (!e.hasAttribute("edge.weight")) {
                        Double source = e.getSourceNode().getAttribute("layout.weight");
                        Double target = e.getTargetNode().getAttribute("layout.weight");
                        Double edgeWeight = source + target;
                        e.setAttribute("edge.weight", edgeWeight);
                        allEdgeWeights.add(edgeWeight);
                    }
                }
            }
        }
    }

    private int addNodes2Stream(Graph graph, TreeSet<Double> allWeights) throws IOException {
        double smallest = smaller(allWeights.first(), allWeights.last());
        double tenth = oneTenth(allWeights.first(), allWeights.last());

        int nodes = 0;

        boolean addComma = false;
        for (Node n : graph.getEachNode()) {
            String name = n.getAttribute("label");
            Double weight = n.getAttribute("layout.weight");
            if (name != null && weight != null) {
                if (addComma) {
                    print(",");
                }
                print("{\"name\":\"%s\",\"group\":\"%s\"}", name, group(weight, smallest, tenth));
                ++nodes;
                addComma = true;
            }
        }
        return nodes;
    }

    private int addEdges2Stream(Graph graph, Map<String, Integer> id2ArrayIndexMapping, List<Double> allEdgeWeights,
            int nodesAdded) throws IOException {
        Collections.sort(allEdgeWeights);
        int edges2Add = nodesAdded * EDGES_2_NODES_MULTIPLICATOR;
        int fromIndex = allEdgeWeights.size() > edges2Add ? allEdgeWeights.size() - edges2Add : 0;
        List<Double> acceptedEdgeWeights = Collections.<Double> emptyList();
        if (!allEdgeWeights.isEmpty()) {
            acceptedEdgeWeights = allEdgeWeights.subList(fromIndex, allEdgeWeights.size() - 1);
        }
        boolean addComma = false;
        int edges = 0;
        for (Edge e : graph.getEachEdge()) {
            Integer source = id2ArrayIndexMapping.get(e.getSourceNode().getId());
            Integer target = id2ArrayIndexMapping.get(e.getTargetNode().getId());
            Double edgeWeight = e.getAttribute("edge.weight");

            if (source != null && target != null && acceptedEdgeWeights.contains(edgeWeight)) {
                if (addComma) {
                    print(",");
                }
                print("{\"source\":%d,\"target\":%d}", source, target);
                ++edges;
                addComma = true;
            }
        }
        return edges;
    }

    private int group(double weight, double smallest, double tenth) {
        int group = Math.abs(new Double((weight - smallest) / tenth).intValue());
        if (group == 0) {
            return 1;
        }
        return group;
    }

    private double oneTenth(double first, double last) {
        double big = first < last ? last : first;
        double small = smaller(first, last);
        return (big - small) / 10;
    }

    private double smaller(double first, double last) {
        return first < last ? first : last;
    }

    public void edgeAttributeAdded(String sourceId, long timeId, String edgeId, String attribute, Object value) {
        throw new UnsupportedOperationException();
    }

    public void edgeAttributeChanged(String sourceId, long timeId, String edgeId, String attribute, Object oldValue,
            Object newValue) {
        throw new UnsupportedOperationException();
    }

    public void edgeAttributeRemoved(String sourceId, long timeId, String edgeId, String attribute) {
        throw new UnsupportedOperationException();
    }

    public void graphAttributeAdded(String sourceId, long timeId, String attribute, Object value) {
        throw new UnsupportedOperationException();
    }

    public void graphAttributeChanged(String sourceId, long timeId, String attribute, Object oldValue, Object newValue) {
        throw new UnsupportedOperationException();
    }

    public void graphAttributeRemoved(String sourceId, long timeId, String attribute) {
        throw new UnsupportedOperationException();
    }

    public void nodeAttributeAdded(String sourceId, long timeId, String nodeId, String attribute, Object value) {
        throw new UnsupportedOperationException();
    }

    public void nodeAttributeChanged(String sourceId, long timeId, String nodeId, String attribute, Object oldValue,
            Object newValue) {
        throw new UnsupportedOperationException();
    }

    public void nodeAttributeRemoved(String sourceId, long timeId, String nodeId, String attribute) {
        throw new UnsupportedOperationException();
    }

    public void edgeAdded(String sourceId, long timeId, String edgeId, String fromNodeId, String toNodeId,
            boolean directed) {
        throw new UnsupportedOperationException();
    }

    public void edgeRemoved(String sourceId, long timeId, String edgeId) {
        throw new UnsupportedOperationException();
    }

    public void graphCleared(String sourceId, long timeId) {
        throw new UnsupportedOperationException();
    }

    public void nodeAdded(String sourceId, long timeId, String nodeId) {
        throw new UnsupportedOperationException();
    }

    public void nodeRemoved(String sourceId, long timeId, String nodeId) {
        throw new UnsupportedOperationException();
    }

    public void stepBegins(String sourceId, long timeId, double step) {
        throw new UnsupportedOperationException();
    }

}
