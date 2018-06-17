package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.core.context.service.Service;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

    public AudioContext() {
        this.commandsQueue = new ArrayBlockingQueue<>(10000);
        this.allSources = Collections.synchronizedList(new LinkedList<>());
        this.playingSources = new ArrayList<>();
        this.freeSources = new ArrayBlockingQueue<>(100);
        this.allBuffers = Collections.synchronizedList(new LinkedList<>());
        this.freeBuffers = new ArrayBlockingQueue<>(100);
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

    BlockingQueue<Integer> getFreeSources() {
        return freeSources;
    }

    BlockingQueue<Integer> getFreeBuffers() {
        return freeBuffers;
    }

    BlockingQueue<Command> getCommandsQueue() {
        return commandsQueue;
    }

    List<AudioSourceProperty> getAllSources() {
        return allSources;
    }

    List<AudioClip> getAllBuffers() {
        return allBuffers;
    }
}
