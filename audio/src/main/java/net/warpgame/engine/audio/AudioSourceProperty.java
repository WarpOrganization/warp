package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.CreateSourceCommand;
import net.warpgame.engine.audio.command.PauseCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.audio.command.StopCommand;
import net.warpgame.engine.core.property.Property;
import org.apache.commons.io.FilenameUtils;
import org.joml.Vector3f;

import java.io.IOException;

public class AudioSourceProperty extends Property {

    public static final String NAME = "source";

    private AudioManager audioManager;
    private AudioContext audioContext;
    private AudioSource audioSource;
    private String soundFilePath;
    private String soundName;

    public AudioSourceProperty(String soundFilePath) {
        super(NAME);
        this.soundFilePath = soundFilePath;
        this.soundName = FilenameUtils.getBaseName(soundFilePath);
    }

    public void play() {
        audioContext.putCommand(new PlayCommand(audioSource, soundName));
    }

    public void stop() {
        throw new UnsupportedOperationException("Stop command is not implemented");
    }

    public void pause() {
        throw new UnsupportedOperationException("Pause command is not implemented");
    }

    public String getSoundName() {
        return soundName;
    }

    @Override
    public void enable() {
        super.enable();
        audioManager = getOwner().getContext().getLoadedContext().findOne(AudioManager.class).get();
        audioContext = getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get();
        audioSource = new AudioSource(getOwner());
        audioContext.putCommand(new CreateSourceCommand(audioSource));
        audioManager.prepereAudioClip(soundFilePath);
    }
}
