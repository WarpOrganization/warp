package net.warpgame.engine.audio;

import net.warpgame.engine.core.property.Property;

public class ListenerProperty extends Property {
    public static final String NAME = "listener";

    private AudioContext audioContext;
    private AudioListener audioListener;

    public ListenerProperty(AudioContext audioContext) {
        super(NAME);
        this.audioContext = audioContext;
        audioListener = new AudioListener(getOwner());
        audioContext.setAudioListener(audioListener);
    }
}
