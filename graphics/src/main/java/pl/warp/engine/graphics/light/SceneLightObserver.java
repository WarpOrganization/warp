package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.graphics.Environment;

import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class SceneLightObserver {

    private Environment environment;
    private Listener<Component, LightAddedEvent> lightAddedEventListener;
    private Listener<Component, LightRemovedEvent> lightRemovedEventListener;

    public SceneLightObserver(Scene scene, Environment environment) {
        this.environment = environment;
        this.lightAddedEventListener =
                SimpleListener.createListener(scene, LightAddedEvent.LIGHT_ADDED_EVENT_NAME, this::handleLightAdded);
        this.lightRemovedEventListener =
                SimpleListener.createListener(scene, LightRemovedEvent.LIGHT_REMOVED_EVENT_NAME, this::handleLightRemoved);
        Set<LightSourceProperty> properties = scene.getChildrenProperties(LightSourceProperty.LIGHT_PROPERTY_NAME);
        addLights(properties);
    }


    private void handleLightAdded(LightAddedEvent event) {
        environment.addSpotLightSource(event.getAddedLight());
    }

    private void handleLightRemoved(LightRemovedEvent event) {
        environment.removeSpotLightSource(event.getRemovedLight());
    }

    private void addLights(Set<LightSourceProperty> properties) {
        for (LightSourceProperty property : properties)
            if (property.isEnabled()) {
                for (SpotLight spotLight : property.getSpotLights())
                    environment.addSpotLightSource(spotLight);
            }
    }

    public void destroy() {
        lightAddedEventListener.destroy();
        lightRemovedEventListener.destroy();
    }
}
