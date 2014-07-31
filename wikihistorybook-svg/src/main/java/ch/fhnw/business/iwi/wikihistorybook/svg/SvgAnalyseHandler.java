package ch.fhnw.business.iwi.wikihistorybook.svg;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Analyzes the generated SVG and collects important properties based on the XML
 * SAX API.
 * 
 * @author Stefan Wagner
 * 
 */
public class SvgAnalyseHandler extends SvgAbstractHandler {

    public SvgAnalyseHandler() {
        allNodes = new SvgAllNodes();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String id = getId(attributes);
        if (isStartingTagOfNode(qName, id)) {
            currentNode = new SvgNode(id);
        }
        if (isPathSvgTag(qName) && isInsideNode()) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if ("d".equals(name)) {
                    String value = attributes.getValue(i);
                    String rx = findACommandWithRxParameter(value);
                    currentNode.setWeight(rx);
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (length > 0 && isInsideNode()) {
            String text = new String(ch, start, length).trim();
            if (text.length() > 0) {
                currentNode.setTitle(text);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (isGSvgTag(qName) && isInsideNode()) {
            allNodes.addSvgNode(currentNode);
            currentNode = null;
        }
    }

    private String findACommandWithRxParameter(String pathcommands) {
        String[] pathCommands = pathcommands.split(" ");
        for (int i = 0; i < pathCommands.length; i++) {
            if ("a".equals(pathCommands[i])) {
                String rxRy = pathCommands[i + 1];
                return rxRy.split(",")[0];
            }
        }
        return null;
    }

}
