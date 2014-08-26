package ch.fhnw.business.iwi.wikihistorybook.graph;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GraphData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer year;
    private Integer maxNodes;
    private Integer nodes;
    private Integer edges;

    public GraphData() {
    }

    public GraphData(int year, int maxNodes) {
        this.year = year;
        this.maxNodes = maxNodes;
    }

    public void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(year);
        stream.writeObject(maxNodes);
        stream.writeObject(nodes);
        stream.writeObject(edges);
    }

    public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        year = (Integer) stream.readObject();
        maxNodes = (Integer) stream.readObject();
        nodes = (Integer) stream.readObject();
        edges = (Integer) stream.readObject();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getMaxNodes() {
        return maxNodes;
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    public Integer getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public Integer getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }

}
