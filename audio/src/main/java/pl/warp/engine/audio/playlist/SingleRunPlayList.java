package pl.warp.engine.audio.playlist;

/**
 * @author Hubertus
 *         Created 22.01.17
 */
public class SingleRunPlayList extends PlayList {

    private int current = -1;

    @Override
    public String getNextFile() {
        current++;
        return current >= files.size() ? null : files.get(current);
    }
}
