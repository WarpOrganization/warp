package net.warpgame.launcher.fileStructure;

/**
 * @author Hubertus
 *         Created 18.06.2017
 */
public class FileElement implements Element{
    private String path;
    private long size;
    private String hash;

    public FileElement(String path, long size, String hash) {
        this.path = path;
        this.size = size;
        this.hash = hash;
    }
    
    public FileElement(){}

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public boolean equalsElement(Element element) {
        return path.equals(element.getPath()) && hash.equals(element.getHash()) && element.isFile();
    }

}
