package pl.warp.launcher.fileStructure;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 18.06.2017
 */
public class FolderElement implements Element {
    private String path;
    private ArrayList<Element> children = new ArrayList<>();

    public FolderElement(String path) {
        this.path = path;
    }

    public FolderElement() {
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getHash() {
        return null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Element> getChildren() {
        return children;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean equalsElement(Element element) {
        return path.equals(element.getPath()) && element.isDirectory();
    }
}
