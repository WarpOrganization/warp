package pl.warp.engine.audio.playlist;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public abstract class PlayList {

    ArrayList<String> paths = new ArrayList<>();

    public void add(String path){
        paths.add(path);
    }

    public abstract String getNextFile();
}
