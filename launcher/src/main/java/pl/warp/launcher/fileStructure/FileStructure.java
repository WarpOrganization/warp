package pl.warp.launcher.fileStructure;

import org.xml.sax.SAXException;
import pl.warp.launcher.PathData;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hubertus
 *         Created 18.06.2017
 */
public class FileStructure {

    private FolderElement mainFolder;

    public FileStructure() {
    }

    public void generate(PathData pathData) {
        mainFolder = new FolderElement();
        FileStructureGenerator generator = new FileStructureGenerator();
        generator.generateStructure(pathData, mainFolder);
    }

    public void importStructure(String xmlPath) {
        mainFolder = new FolderElement();
        try {
            InputStream stream = new FileInputStream(new File(xmlPath));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            FileStructureImporter importer = new FileStructureImporter(mainFolder);
            saxParser.parse(stream, importer);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void importStructure(PathData data) {
        importStructure(data.getLauncherDataPath() + "/structure.xml");
    }

    public FolderElement getMainFolder() {
        return mainFolder;
    }
}
