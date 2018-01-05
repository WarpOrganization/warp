package net.warpgame.engine.audio.playlist;

/**
 * @author Hubertus
 *         Created 22.01.17
 */
public class SingleRunPlayList extends PlayList {

    private int current = -1;

    @Override
    public String getNextFile() {
        current++;
        return current >= paths.size() ? null : paths.get(current);
    }
}
