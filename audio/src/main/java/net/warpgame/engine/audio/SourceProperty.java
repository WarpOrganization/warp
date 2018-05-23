package net.warpgame.engine.audio;

import net.warpgame.engine.core.property.Property;
import org.apache.commons.io.FilenameUtils;
import org.joml.Vector3f;

import java.io.IOException;

public class SourceProperty extends Property {

    public static final String NAME = "source";

    private AudioManager audioManager;
    private AudioSource audioSource;
    private String soundFilePath;
    private String soundName;

    public SourceProperty(String soundFilePath) {
        super(NAME);
        this.soundFilePath = soundFilePath;
        this.soundName = FilenameUtils.getBaseName(soundFilePath);
    }

    public void play() {
        audioManager.play(audioSource, soundName);
    }

    public void stop() {
        audioManager.stop(audioSource);
    }

    public void pause() {
        audioManager.pause(audioSource);
    }

    public String getSoundName() {
        return soundName;
    }

    @Override
    public void enable() {
        super.enable();
        audioManager = getOwner().getContext().getLoadedContext().findOne(AudioManager.class).get();
        audioSource = audioManager.createPersistentSource(getOwner(), new Vector3f(0, 0, 0));
        audioManager.prepereAudioClip(soundFilePath);
    }
}
