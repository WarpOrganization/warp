package net.warpgame.engine.audio;

import net.warpgame.engine.core.property.Property;

public class AudioListenerProperty extends Property {

    public AudioListenerProperty() {

    }

    @Override
    public void enable() {
        super.enable();
        getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get().setListener(this);
    }
}
