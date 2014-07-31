package ch.fhnw.business.iwi.wikihistorybook.svg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Contains all SVG Wikihistory graph nodes in a
 * <ul>
 * <li>{@link Map} by node id</li>
 * <li>{@link TreeSet} sorted by weight attribute</li>
 * </ul>
 * 
 * @author Stefan Wagner
 */
public class SvgAllNodes {

    private Map<String, SvgNode> nodesById = new HashMap<String, SvgNode>();
    private TreeSet<SvgNode> sortedNodes = new TreeSet<SvgNode>();
    private List<SvgNode> top = new ArrayList<SvgNode>();

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

    public void computeTop(final int amountOfMostImportant) {
        int amount = amountOfMostImportant;
        Iterator<SvgNode> iterator = sortedNodes.descendingIterator();
        int i = 0;
        int max = sortedNodes.size();
        while (iterator.hasNext() && i < amount && i < max) {
            top.add(iterator.next());
            i++;
        }
    }

    public boolean isTop(SvgNode currentNode) {
        return top.contains(currentNode);
    }

}
