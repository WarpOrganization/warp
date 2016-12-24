package pl.warp.engine.audio;

import org.joml.Vector3f;
import pl.warp.engine.audio.command.CreateSourceCommand;
import pl.warp.engine.audio.command.PlayCommand;
import pl.warp.engine.core.scene.Component;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by hubertus on 22.12.16.
 */
public class AudioManager {

    private AudioContext audioContext;

    public AudioManager(AudioContext audioContext) {

        this.audioContext = audioContext;
    }

    public AudioSource createPersistentSource(Component component, Vector3f offset, boolean isRelative) {
        AudioSource source = new AudioSource(component, offset, isRelative, true);
        audioContext.putCommand(new CreateSourceCommand(source));
        return source;
    }

    public void play(AudioSource source, String soundName) {
        audioContext.putCommand(new PlayCommand(source, soundName));
    }

    private Vector3f emptyVector = new Vector3f();

    //TODO optional relativity
    public void playSingle(Component owner, String soundName) {
        playSingle(owner, soundName, emptyVector);
    }

    public void playSingle(Component owner, String soundName, Vector3f offset) {
        AudioSource source = new AudioSource(owner, offset, true, false);
        audioContext.putCommand(new CreateSourceCommand(source));
        audioContext.putCommand(new PlayCommand(source, soundName));
    }

    public void loadFiles(String path) {
        try {
            audioContext.getSoundBank().loadDir(path);
        } catch (URISyntaxException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void stop(AudioSource source) {

    }

    public void pause() {

    }

}
