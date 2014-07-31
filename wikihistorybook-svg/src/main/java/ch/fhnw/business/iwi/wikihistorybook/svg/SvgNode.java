package ch.fhnw.business.iwi.wikihistorybook.svg;

/**
 * Container for a SVG Wikihistory graph node.
 * 
 * @author Stefan Wagner
 *
 */
public class SvgNode implements Comparable<SvgNode> {

    private String id;
    private String title;
    private Double weight;
    
    public SvgNode(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(SvgNode other) {
        return this.weight.compareTo(other.weight);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setWeight(String weight) {
        this.weight = Double.valueOf(weight);
    }

    @Override
    public String toString() {
        return id + " " + weight;
    }

}
