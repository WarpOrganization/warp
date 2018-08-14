package net.warpgame.ide;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class TreeLoader {
    private static class TreeItemCreationContentHandler extends DefaultHandler
    {

        private TreeNode item;

        TreeItemCreationContentHandler(String rootname)
        {
            item = new TreeNode(rootname);
        }

        @Override
        public void endElement(String uri, String localName, String qName)
        {
            item = item.getRealParent();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            TreeNode item = new TreeNode(attributes.getValue("path"));
            this.item.addRealChild(item);
            this.item = item;
        }

        @Override
        public void characters(char[] ch, int start, int length)
        {
            String s = String.valueOf(ch, start, length).trim();
            if (!s.isEmpty())
            {
                this.item.addRealChild(new TreeNode(s));
            }
        }

    }

    public static TreeNode readData(File file) throws SAXException, ParserConfigurationException, IOException
    {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        TreeItemCreationContentHandler contentHandler = new TreeItemCreationContentHandler(file.getPath());
        reader.setContentHandler(contentHandler);
        reader.parse(file.toURI().toString());
        return contentHandler.item;
    }

    public static TreeNode readData(String text) throws SAXException, ParserConfigurationException, IOException
    {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        TreeItemCreationContentHandler contentHandler = new TreeItemCreationContentHandler("text");
        reader.setContentHandler(contentHandler);
        reader.parse(new InputSource(new StringReader(text)));
        if(contentHandler.item.getRealChildren().size() == 1) return contentHandler.item.getRealChildren().get(0);
        return new TreeNode("Something went wrong.");
    }
}
