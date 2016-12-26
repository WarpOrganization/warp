package pl.warp.engine.audio;

import org.joml.Vector3f;
import pl.warp.engine.audio.command.CreateSourceCommand;
import pl.warp.engine.audio.command.PlayCommand;
import pl.warp.engine.core.scene.Component;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public class AudioManager {

    private AudioContext audioContext;

    public AudioManager(AudioContext audioContext) {

        this.audioContext = audioContext;
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

    public void play(AudioSource source, String soundName) {
        audioContext.putCommand(new PlayCommand(source, soundName));
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
