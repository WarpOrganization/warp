package net.warpgame.engine.audio.playlist;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class PlayOneByOnePlayList extends PlayList {

    int current = -1;

    @Override
    public String getNextFile() {
        if (current == paths.size() - 1) current = 0;
        else current++;
        return paths.get(current);
    }
}