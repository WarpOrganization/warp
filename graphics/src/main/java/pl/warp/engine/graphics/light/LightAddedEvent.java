package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Event;
import pl.warp.engine.graphics.light.DirectionalSpotLight;
import pl.warp.engine.graphics.light.SpotLight;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class LightAddedEvent extends Event {
    public static String LIGHT_ADDED_EVENT_NAME = "lightAddedEvent";

    private DirectionalSpotLight[] directionalLightsAdded;
    private SpotLight[] spotLightsAdded;

    public LightAddedEvent(DirectionalSpotLight[] directionalLightsAdded, SpotLight[] spotLightsAdded) {
        super(LIGHT_ADDED_EVENT_NAME);
        this.directionalLightsAdded = directionalLightsAdded;
        this.spotLightsAdded = spotLightsAdded;
    }

    public DirectionalSpotLight[] getDirectionalLightsAdded() {
        return directionalLightsAdded;
    }

    public SpotLight[] getSpotLightsAdded() {
        return spotLightsAdded;
    }
}
