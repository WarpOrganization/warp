package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.core.context.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */
@Service
public class AudioContext {

    private AudioListenerProperty audioListener;
    private List<AudioSourceProperty> playing;
    private BlockingQueue<Command> commandsQueue;
    private SoundBank soundBank;

    public AudioContext() {
        playing = new ArrayList<>();
        commandsQueue = new ArrayBlockingQueue<Command>(10000);
        soundBank = new SoundBank(this);
    }

    void putCommand(Command cmd) {
        try {
            commandsQueue.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AudioSourceProperty> getPlaying() {
        return playing;
    }

    BlockingQueue<Command> getCommandsQueue() {
        return commandsQueue;
    }

    AudioListenerProperty getAudioListener() {
        return audioListener;
    }

    void setAudioListener(AudioListenerProperty audioListener) {
        this.audioListener = audioListener;
    }

    public SoundBank getSoundBank() {
        return soundBank;
    }

}
