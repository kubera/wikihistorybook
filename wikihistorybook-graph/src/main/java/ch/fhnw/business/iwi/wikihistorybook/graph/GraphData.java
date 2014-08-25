package ch.fhnw.business.iwi.wikihistorybook.graph;

public class GraphData {

    private Integer year;
    private Integer maxNodes;
    private Integer nodes;
    private Integer edges;
    
    public GraphData(int year, int maxNodes) {
        this.year = year;
        this.maxNodes = maxNodes;
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
