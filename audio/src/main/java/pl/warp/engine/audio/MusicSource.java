package pl.warp.engine.audio;

import org.joml.Vector3f;
import pl.warp.engine.audio.playlist.PlayList;
import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class MusicSource extends AudioSource {

    private PlayList playList;
    private Queue<Integer> buffers = new LinkedList<>();
    private AudioInputStream stream;
    private AudioFormat format;
    private int openALFormat;
    private float sampleRate;
    private int currentCycles;
    private boolean doneReading;

    public MusicSource(Component owner, Vector3f offset, boolean isPersistent, PlayList playList) {
        super(owner, offset, isPersistent);
        this.playList = playList;
    }

    public MusicSource(Vector3f offset, boolean isPersistent, PlayList playList) {
        super(offset, isPersistent);
        this.playList = playList;
    }

    public void loadNew(String path) {
        try {
            stream = AudioSystem.getAudioInputStream(new File(EngineContext.CODESOURCE_DIR + path));
            format = stream.getFormat();
            openALFormat = SoundBank.getOpenALFormat(format);
            sampleRate = format.getSampleRate();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
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

    public AudioInputStream getStream() {
        return stream;
    }

    public AudioFormat getFormat() {
        return format;
    }

    public int getOpenALFormat() {
        return openALFormat;
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public int getCurrentCycles() {
        return currentCycles;
    }

    public void incrementCurrentCycles() {
        currentCycles++;
    }

    public boolean isDoneReading() {
        return doneReading;
    }

    void doneReading() {
        doneReading = true;
    }

    public void keepReading() {
        doneReading = false;
    }
}
