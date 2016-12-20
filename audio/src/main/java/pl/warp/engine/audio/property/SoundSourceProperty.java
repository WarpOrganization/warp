package pl.warp.engine.audio.property;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * Created by huber on 20.12.2016.
 */
public class SoundSourceProperty extends Property {

    public static final String SOUND_SOURCE_PROPERTY_NAME = "soundSourceProperty";
    private AudioContext context;

    public SoundSourceProperty(Component owner, AudioContext context) {
        super(owner, SOUND_SOURCE_PROPERTY_NAME);
        this.context = context;
    }
}
