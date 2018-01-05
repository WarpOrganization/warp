package net.warpgame.launcher.fileStructure;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Hubertus
 *         Created 24.06.2017
 */
public class FileStructureImporter extends DefaultHandler {
    private FolderElement mainFolder;
    Deque<FolderElement> folderStack = new ArrayDeque<>();

    public FileStructureImporter(FolderElement mainFolder) {
        this.mainFolder = mainFolder;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("folder")) {
            if (folderStack.isEmpty()) {
                mainFolder.setPath(attributes.getValue("path").replace('\\', '/'));
                folderStack.add(mainFolder);
            } else {

                FolderElement folderElement = new FolderElement();
                folderElement.setPath(attributes.getValue("path").replace('\\', '/'));
                folderStack.peekLast().getChildren().add(folderElement);
                folderStack.add(folderElement);
            }
        } else if (qName.equalsIgnoreCase("file")) {
            FileElement fileElement = new FileElement();
            fileElement.setPath(attributes.getValue("path").replace('\\', '/'));
            fileElement.setHash(attributes.getValue("hash"));
            fileElement.setSize(Integer.parseInt(attributes.getValue("size")));
            folderStack.peekLast().getChildren().add(fileElement);
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("folder")) folderStack.removeLast();
    }
}
