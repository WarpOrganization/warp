package net.warpgame.launcher.fileStructure;

/**
 * @author Hubertus
 *         Created 18.06.2017
 */
public interface Element {
    boolean isDirectory();
    boolean isFile();
    String getPath();
    String getHash();
    boolean equalsElement(Element element);
}
