package net.warpgame.engine.audio.property;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.core.property.Property;

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
