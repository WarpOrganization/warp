package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */
@Service
public class AudioContext {

    private BlockingQueue<Command> commandsQueue;
    private AudioListenerProperty listener;

    private List<AudioSourceProperty> allSources;
    private List<AudioSourceProperty> playingSources;
    private BlockingQueue<Integer> freeSources;

    private List<AudioClip> allBuffers;
    private BlockingQueue<Integer> freeBuffers;

    private SoundBank soundBank;

    public AudioContext() {
        this.playingSources = new ArrayList<>();
        this.commandsQueue = new ArrayBlockingQueue<>(10000);
        this.freeSources = new ArrayBlockingQueue<>(100);
        this.freeBuffers = new ArrayBlockingQueue<>(100);
        this.soundBank = new SoundBank(this);
    }

    public List<AudioSourceProperty> getPlayingSources() {
        return playingSources;
    }

    AudioListenerProperty getListener() {
        return listener;
    }

    void setListener(AudioListenerProperty listener) {
        this.listener = listener;
    }

    public SoundBank getSoundBank() {
        return soundBank;
    }

    void putCommand(Command cmd) {
        try {
            commandsQueue.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int getSource() throws InterruptedException {
        return freeSources.take();
    }

    int getBuffer() throws InterruptedException {
        return freeBuffers.take();
    }

    public BlockingQueue<Integer> getFreeSources() {
        return freeSources;
    }

    public BlockingQueue<Integer> getFreeBuffers() {
        return freeBuffers;
    }

    BlockingQueue<Command> getCommandsQueue() {
        return commandsQueue;
    }
}
