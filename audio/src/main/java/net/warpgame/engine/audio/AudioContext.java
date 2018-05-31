package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
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

    private AudioListenerProperty listener;
    private List<AudioSourceProperty> sources;
    private List<AudioSourceProperty> playing;
    private SoundBank soundBank;
    private AudioThread audioThread;

    public AudioContext(AudioThread audioThread) {
        this.audioThread = audioThread;
        this.playing = new ArrayList<>();
        this.soundBank = new SoundBank(this);
    }

    void prepareAudioClip(String clip) {
        String name = FilenameUtils.getBaseName(clip);
        if(!soundBank.containsSound(name)){
            audioThread.scheduleOnce(() -> {
                try {
                    soundBank.loadFile(clip);
                } catch (IOException e) {
                    throw new RuntimeException(String.format("Can't find filed named %s", clip));
                }
            });

        }
    }

    public List<AudioSourceProperty> getPlaying() {
        return playing;
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

}
