package pl.warp.engine.audio.playlist;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public abstract class PlayList {

    ArrayList<String> files = new ArrayList<>();

    public void add(String path){
        files.add(path);
    }

    public abstract String getNextFile();
}
