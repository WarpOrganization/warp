package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Event;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class LightRemovedEvent extends Event {
    public static String LIGHT_REMOVED_EVENT_NAME = "lightRemovedEvent";

    private SpotLight removedLight;

    public LightRemovedEvent(SpotLight removedLight) {
        super(LIGHT_REMOVED_EVENT_NAME);
        this.removedLight = removedLight;
    }

    public SpotLight getRemovedLight() {
        return removedLight;
    }
}
