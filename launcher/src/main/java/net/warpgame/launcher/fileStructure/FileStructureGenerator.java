package net.warpgame.launcher.fileStructure;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import net.warpgame.launcher.PathData;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Hubertus
 *         Created 24.06.2017
 */
public class FileStructureGenerator {

    public void generateStructure(PathData data, FolderElement mainFolder) {
        Deque<FolderElement> folderStack = new ArrayDeque<>();
        folderStack.add(mainFolder);
        File structureFile = new File(data.getLauncherDataPath() + "/structure.xml");
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            org.w3c.dom.Element rootFolder = doc.createElement("folder");
            rootFolder.setAttribute("path", data.getFilesPath());
            mainFolder.setPath(data.getFilesPath());
            mainFolder.getPath().replace('\\', File.separatorChar);
            doc.appendChild(rootFolder);

            rootFolder.appendChild(parseFolder(data.getFilesPath(), doc, folderStack));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(structureFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private String hashFile(String path) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(path)));
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }

    private DocumentFragment parseFolder(String path, Document doc, Deque<FolderElement> folderStack) {
        DocumentFragment fragment = doc.createDocumentFragment();
        File[] files = new File(path).listFiles();
        if (files == null) return fragment;
        for (int i = 0; i < files.length; i++) {
            String currentPath = files[i].getPath();
            currentPath = currentPath.replace('\\', '/');
            if (files[i].isDirectory()) {
                FolderElement folderElement = new FolderElement(currentPath);
                folderStack.peekLast().getChildren().add(folderElement);
                folderStack.add(folderElement);
                org.w3c.dom.Element folder = doc.createElement("folder");
                folder.setAttribute("path", currentPath);
                fragment.appendChild(folder);
                folder.appendChild(parseFolder(currentPath, doc, folderStack));
                folderStack.removeLast();
            }
            if (files[i].isFile()) {
                FileElement fileElement = new FileElement(currentPath, files[i].length(), hashFile(currentPath));
                folderStack.peekLast().getChildren().add(fileElement);
                org.w3c.dom.Element file = doc.createElement("file");
                file.setAttribute("path", fileElement.getPath());
                file.setAttribute("size", fileElement.getSize() + "");
                file.setAttribute("hash", fileElement.getHash());
                fragment.appendChild(file);
            }
        }
        return fragment;
    }

}
