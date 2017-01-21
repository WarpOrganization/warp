package pl.warp.engine.audio;

import org.joml.Vector3f;
import pl.warp.engine.audio.playlist.PlayList;
import pl.warp.engine.core.scene.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class MusicSource extends AudioSource{

    private PlayList playList;
    private Queue<Integer> buffers = new LinkedList<>();

    public MusicSource(Component owner, Vector3f offset, boolean isPersistent) {
        super(owner, offset, isPersistent);
    }

    public MusicSource(Vector3f offset, boolean isPersistent, PlayList playList) {
        super(offset, isPersistent);
        this.playList = playList;
    }

    public PlayList getPlayList() {
        return playList;
    }

    public void setPlayList(PlayList playList) {
        this.playList = playList;
    }

    public Queue<Integer> getBuffers() {
        return buffers;
    }
}
