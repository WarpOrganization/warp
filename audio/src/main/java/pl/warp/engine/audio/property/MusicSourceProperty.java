package pl.warp.engine.audio.property;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * Created by huber on 20.12.2016.
 */
public class MusicSourceProperty extends Property {

    public static final String MUSIC_SOURCE_PROPERTY_NAME = "musicSourceProperty";

    private int sourceId;

    public MusicSourceProperty(Component owner, AudioContext context) {
        super(owner, MUSIC_SOURCE_PROPERTY_NAME);
    }

}
