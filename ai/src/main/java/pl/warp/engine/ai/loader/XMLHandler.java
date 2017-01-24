package pl.warp.engine.ai.loader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class XMLHandler extends DefaultHandler {

    private BehaviourTreeBuilder builder;
    private Deque<NodeBuilder> stack = new ArrayDeque<>();
    private boolean hasEnded = false;


    public XMLHandler(BehaviourTreeBuilder builder) {

        this.builder = builder;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String path = attributes.getValue("path");
        if (qName.equalsIgnoreCase("basenode")) {
            stack.push(new NodeBuilder());
            stack.peek().setPath(path);
            builder.setBaseNode(stack.peek());
        } else if (qName.equalsIgnoreCase("node")) {
                NodeBuilder node = new NodeBuilder();
                node.setPath(path);
                stack.peek().getChildren().add(node);
                stack.push(node);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        stack.pop();
    }
}
