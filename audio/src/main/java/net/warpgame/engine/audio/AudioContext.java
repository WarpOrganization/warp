package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */
public class AudioContext {

    private AudioListener audioListener;
    private List<AudioSource> playing;
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

    public List<AudioSource> getPlaying() {
        return playing;
    }

    public BlockingQueue<Command> getCommandsQueue() {
        return commandsQueue;
    }

    public AudioListener getAudioListener() {
        return audioListener;
    }

    public void setAudioListener(AudioListener audioListener) {
        this.audioListener = audioListener;
    }

    public SoundBank getSoundBank() {
        return soundBank;
    }

}
