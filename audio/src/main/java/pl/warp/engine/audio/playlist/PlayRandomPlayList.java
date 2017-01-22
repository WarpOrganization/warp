package pl.warp.engine.audio.playlist;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class PlayRandomPlayList extends PlayList {

    @Override
    public String getNextFile() {
        return files.get((int) (Math.random() * files.size()));
    }
}
