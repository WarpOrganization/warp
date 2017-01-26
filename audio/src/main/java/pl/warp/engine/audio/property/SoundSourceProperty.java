package pl.warp.engine.audio.property;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.core.scene.Property;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */
public class SoundSourceProperty extends Property {

    public static final String SOUND_SOURCE_PROPERTY_NAME = "soundSourceProperty";
    private AudioContext context;

    public SoundSourceProperty(AudioContext context) {
        super(SOUND_SOURCE_PROPERTY_NAME);
        this.context = context;
    }
}
