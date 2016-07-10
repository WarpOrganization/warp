package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Event;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class LightAddedEvent extends Event {
    public static String LIGHT_ADDED_EVENT_NAME = "lightAddedEvent";

    private SpotLight addedLight;

    public LightAddedEvent(SpotLight addedLight) {
        super(LIGHT_ADDED_EVENT_NAME);
        this.addedLight = addedLight;
    }

    public SpotLight getAddedLight() {
        return addedLight;
    }
}
