package net.warpgame.engine.audio.playlist;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class PlayRandomPlayList extends PlayList {

    @Override
    public String getNextFile() {
        return paths.get((int) (Math.random() * paths.size()));
    }
}
