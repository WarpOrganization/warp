package pl.warp.engine.audio;

import pl.warp.engine.audio.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */
public class AudioContext {

    private Listener listener;
    private List<AudioSource> sourceList;
    private List<AudioSource> playing;
    private BlockingQueue<Command> commandsQueue;
    private SoundBank soundBank;

    public AudioContext() {
        sourceList = new ArrayList<>();
        playing = new ArrayList<>();
        commandsQueue = new ArrayBlockingQueue<Command>(10000);
        soundBank = new SoundBank();
    }

    void putCommand(Command cmd) {
        try {
            commandsQueue.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AudioSource> getPlaying() {
        return playing;
    }

    protected List<AudioSource> getRegisteredSources() {
        return sourceList;
    }

    public BlockingQueue<Command> getCommandsQueue() {
        return commandsQueue;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public SoundBank getSoundBank() {
        return soundBank;
    }
}
