package net.warpgame.engine.audio;

import net.warpgame.engine.core.property.Property;

public class ListenerProperty extends Property {
    public static final String NAME = "listener";

    private AudioListener audioListener;

    public ListenerProperty() {
        super(NAME);

    }

    @Override
    public void enable() {
        super.enable();
        audioListener = new AudioListener(getOwner());
        getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get().setAudioListener(audioListener);
    }

    public AudioListener getAudioListener() {
        return audioListener;
    }
}
