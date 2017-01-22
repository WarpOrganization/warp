package pl.warp.engine.ai.loader;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class BehaviourTreeLoader {

    public static BehaviourTreeBuilder loadXML(String path) {

        File file = new File(BehaviourTreeLoader.class.getClassLoader().getResource(path).getFile());
        SAXParserFactory factory = SAXParserFactory.newInstance();
        BehaviourTreeBuilder behaviourTreeBuilder = new BehaviourTreeBuilder();
        try {
            SAXParser saxParser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler(behaviourTreeBuilder);
            saxParser.parse(file, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return behaviourTreeBuilder;
    }

}
