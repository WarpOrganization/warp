package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.audio.command.CreateAudioClipCommand;
import net.warpgame.engine.audio.decoder.SoundData;
import net.warpgame.engine.audio.decoder.SoundDecoderManager;
import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    Map<String, Integer> sounds = new TreeMap<>();
    private BlockingQueue<Command> commandsQueue;
    private SoundBank soundBank;

    private AudioThread audioThread;

    public AudioContext(AudioThread audioThread) {
        this.audioThread = audioThread;
        this.playing = new ArrayList<>();
        this.commandsQueue = new ArrayBlockingQueue<>(10000);
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

    void initAudioClip(AudioClip audioClip){
        if(!sounds.containsKey(audioClip.getFile())){
            try {
                SoundData data = SoundDecoderManager.decode(audioClip.getFile());
                this.putCommand(new CreateAudioClipCommand(audioClip, data, sounds));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            audioClip.setId(sounds.get(audioClip.getFile()));
        }
    }

    void putCommand(Command cmd) {
        try {
            commandsQueue.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    BlockingQueue<Command> getCommandsQueue() {
        return commandsQueue;
    }
}
