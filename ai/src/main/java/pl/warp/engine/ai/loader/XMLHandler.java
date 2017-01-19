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
            if (hasEnded) {
                stack.pop();
                stack.push(new NodeBuilder());
                stack.peek().setPath(path);
            } else {
                NodeBuilder node = new NodeBuilder();
                node.setPath(path);
                stack.peek().getChildren().add(node);
                stack.push(node);
            }
            hasEnded = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        hasEnded = true;
    }
}
