package net.warpgame.engine.audio;

import net.warpgame.engine.core.property.Property;

public class AudioListenerProperty extends Property {
    public static final String NAME = "listener";


    public AudioListenerProperty() {
        super(NAME);

    }

    @Override
    public void enable() {
        super.enable();
        getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get().setAudioListener(this);
    }
}
