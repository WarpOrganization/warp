package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.SimpleListener;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class SceneLightingObserver {

    private LightEnvironment lightEnvironment;
    private Listener<Component, LightAddedEvent> lightAddedEventListener;
    private Listener<Component, LightRemovedEvent> lightRemovedEventListener;

    public SceneLightingObserver(Scene scene, LightEnvironment environment) {
        Component root = scene.getRoot();
        this.lightAddedEventListener =
                SimpleListener.createListener(root, LightAddedEvent.LIGHT_ADDED_EVENT_NAME, this::handleLightAdded);
        this.lightRemovedEventListener =
                SimpleListener.createListener(root, LightRemovedEvent.LIGHT_REMOVED_EVENT_NAME, this::handleLightRemoved);
        this.lightEnvironment = environment;

    }

    private void handleLightAdded(LightAddedEvent event) {
        for (SpotLight spotLight : event.getSpotLightsAdded())
            lightEnvironment.addSpotLightSource(spotLight);
        for (DirectionalSpotLight directionalLight : event.getDirectionalLightsAdded())
            lightEnvironment.addDirectionalLightSource(directionalLight);
    }

    private void handleLightRemoved(LightRemovedEvent event) {
        for (SpotLight spotLight : event.getSpotLightsRemoved())
            lightEnvironment.removeSpotLightSource(spotLight);
        for (DirectionalSpotLight directionalLight : event.getDirectionalLightsRemoved())
            lightEnvironment.addDirectionalLightSource(directionalLight);
    }

    public void destroy() {
        lightAddedEventListener.destroy();
        lightRemovedEventListener.destroy();
    }
}
