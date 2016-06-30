package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Event;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class LightRemovedEvent extends Event {
    public static String LIGHT_ADDED_EVENT_NAME = "lightRemovedEvent";

    private DirectionalSpotLight[] directionalLightsRemoved;
    private SpotLight[] spotLightsRemoved;

    public LightRemovedEvent(DirectionalSpotLight[] directionalLightsRemoved, SpotLight[] spotLightsAdded) {
        super(LIGHT_ADDED_EVENT_NAME);
        this.directionalLightsRemoved = directionalLightsRemoved;
        this.spotLightsRemoved = spotLightsAdded;
    }

    public DirectionalSpotLight[] getDirectionalLightsRemoved() {
        return directionalLightsRemoved;
    }

    public SpotLight[] getSpotLightsRemoved() {
        return spotLightsRemoved;
    }
}
