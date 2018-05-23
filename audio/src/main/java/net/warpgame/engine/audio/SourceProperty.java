package net.warpgame.engine.audio;

import net.warpgame.engine.core.property.Property;
import org.joml.Vector3f;

public class SourceProperty extends Property {

    public static final String NAME = "source";

    private AudioManager audioManager;
    private AudioSource audioSource;
    private String soundName;

    public SourceProperty() {
        super(NAME);
    }

    public SourceProperty(String soundName) {
        super(NAME);
        this.soundName = soundName;
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

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    @Override
    public void enable() {
        super.enable();
        audioManager = getOwner().getContext().getLoadedContext().findOne(AudioManager.class).get();
        audioSource = audioManager.createPersistentSource(getOwner(), new Vector3f(0, 0, 0));
    }
}
