package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.*;
import net.warpgame.engine.audio.playlist.PlayList;
import org.joml.Vector3f;
import net.warpgame.engine.audio.command.*;
import net.warpgame.engine.core.component.Component;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public class AudioManager {

    public static AudioManager INSTANCE; //TODO remove

    private AudioContext audioContext;

    public AudioManager(AudioContext audioContext) {
        INSTANCE = this;
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

    public MusicSource createMusicSource(Component owner, Vector3f offset, PlayList playlist) {
        MusicSource source = new MusicSource(owner, offset, true, playlist);
        audioContext.putCommand(new CreateMusicSourceCommand(source));
        return source;
    }

    public MusicSource createMusicSource(Vector3f offset, PlayList playList) {
        MusicSource source = new MusicSource(offset, true, playList);
        audioContext.putCommand(new CreateMusicSourceCommand(source));
        return source;
    }

    public void play(AudioSource source, String soundName) {
        audioContext.putCommand(new PlayCommand(source, soundName));
    }

    public void play(MusicSource source){
        audioContext.putCommand(new PlayMusicCommand(source));
    }

    public void pause(AudioSource source){
        audioContext.putCommand(new PauseCommand(source));
    }

    public void deleteSorce(AudioSource source){
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

    public void loadFiles(String path) {
        try {
            audioContext.getSoundBank().loadDir(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(AudioSource source) {

    }

    public void pause() {

    }

}
