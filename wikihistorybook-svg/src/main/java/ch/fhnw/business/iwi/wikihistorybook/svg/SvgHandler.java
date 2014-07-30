package ch.fhnw.business.iwi.wikihistorybook.svg;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public abstract class SvgHandler extends DefaultHandler {

    protected SvgAllNodes allNodes = new SvgAllNodes();
    protected SvgNode currentNode = null;

    protected boolean isInsideNode() {
        return currentNode != null;
    }

    protected boolean isStartingTagOfNode(String qName, String id) {
        return isGSvgTag(qName) && id != null && id.startsWith("node-") && !isInsideNode();
    }

    protected boolean isGSvgTag(String qName) {
        return "g".equals(qName);
    }

    protected boolean isPathSvgTag(String qName) {
        return "path".equals(qName);
    }

    protected boolean isTextSvgTag(String qName) {
        return "text".equals(qName);
    }

    protected boolean isSvgTag(String qName) {
        return "svg".equals(qName);
    }

    protected boolean isTagContainingTitleOfNode(String qName) {
        return isInsideNode() && (isGSvgTag(qName) || isTextSvgTag(qName));
    }

    public SvgAllNodes getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(SvgAllNodes allNodes) {
        this.allNodes = allNodes;
    }

    protected String getId(Attributes attributes) {
        String id = attributes.getValue("id");
        if (id != null) {
            id = id.trim();
        }
        return id;
    }

}
