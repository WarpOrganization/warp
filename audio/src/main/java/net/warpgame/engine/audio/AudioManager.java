package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.CreateSourceCommand;
import net.warpgame.engine.audio.command.DisposeSourceCommand;
import net.warpgame.engine.audio.command.PauseCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.FilenameUtils;
import org.joml.Vector3f;

import java.io.IOException;

/**
 * @author Hubertus
 *         Created 22.12.16
 */

@Service
public class AudioManager {

    private AudioContext audioContext;
    private AudioThread audioThread;

    public AudioManager(AudioContext audioContext, AudioThread audioThread) {
        this.audioContext = audioContext;
        this.audioThread = audioThread;
    }

    public AudioSource createPersistentSource(Component owner, Vector3f offset) {
        AudioSource source = new AudioSource(owner, offset, true);
        audioContext.putCommand(new CreateSourceCommand(source));
        return source;
    }

    public AudioSource createPersistentSource(Vector3f offset) {
        AudioSource source = new AudioSource(offset, true);
        audioContext.putCommand(new CreateSourceCommand(source));
        return source;
    }

    void pause(AudioSource source){
        audioContext.putCommand(new PauseCommand(source));
    }

    void deleteSorce(AudioSource source){
        audioContext.putCommand(new DisposeSourceCommand(source));
    }

    private Vector3f emptyVector = new Vector3f();

    public void playSingleRelative(String soundName) {
        playSingle(new AudioSource(emptyVector, false), soundName);
    }

    public void playSingleRelative(String soundName, Vector3f offset) {
        playSingle(new AudioSource(offset, false), soundName);
    }

    public void playSingle(Component owner, String soundName) {
        playSingle(new AudioSource(owner, emptyVector, false), soundName);
    }

    public void playSingle(Component owner, String soundName, Vector3f offset) {
        playSingle(new AudioSource(owner, offset, false), soundName);
    }

    private void playSingle(AudioSource source, String soundName) {
        audioContext.putCommand(new CreateSourceCommand(source));
        audioContext.putCommand(new PlayCommand(source, soundName));
    }

    void play(AudioSource source, String soundName) {
        audioContext.putCommand(new PlayCommand(source, soundName));
    }

    public void loadFiles(String path) {
        try {
            audioContext.getSoundBank().loadDir(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void prepereAudioClip(String clip) {
        String name = FilenameUtils.getBaseName(clip);
        SoundBank soundBank = audioContext.getSoundBank();
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

    void stop(AudioSource source) {
        throw new UnsupportedOperationException();
    }

    void pause() {
        throw new UnsupportedOperationException();
    }

}
