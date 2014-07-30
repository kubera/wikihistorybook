package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SvgAllNodes {

    private Map<String, SvgNode> nodesById = new HashMap<String, SvgNode>();
    private Set<SvgNode> sortedNodes = new TreeSet<SvgNode>();
    
    public void addSvgNode(SvgNode node) {
        nodesById.put(node.getId(), node);
        sortedNodes.add(node);
    }

    public SvgNode getSvgNode(String id) {
        return nodesById.get(id);
    }

    public boolean contains(String id) {
        return nodesById.containsKey(id);
    }
}
