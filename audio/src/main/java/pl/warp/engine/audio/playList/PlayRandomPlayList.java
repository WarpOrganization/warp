package pl.warp.engine.audio.playList;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class PlayRandomPlayList extends PlayList {

    @Override
    String GetNextFile() {
        return files.get((int) (Math.random() * files.size()));
    }
}
