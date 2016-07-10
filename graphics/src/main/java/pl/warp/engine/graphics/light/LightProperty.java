package pl.warp.engine.graphics.light;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hubertus on 2016-06-26.
 */

public class LightProperty extends Property<Component> {

    private Set<SpotLight> spotLights = new HashSet<>();

    public static final String LIGHT_PROPERTY_NAME = "lightProperty";

    public LightProperty(Component owner, Set<SpotLight> spotLights) {
        super(owner);
        this.spotLights = spotLights;
        triggerAllAddedEvent();
    }

    public LightProperty(Component owner) {
        super(owner);
    }

    public void addSpotLight(SpotLight light) {
        this.spotLights.add(light);
        getOwner().triggerOnRoot(new LightAddedEvent(light));
    }

    public Set<SpotLight> getSpotLights() {
        return spotLights;
    }

    @Override
    public void enable() {
        if (!isEnabled()) triggerAllAddedEvent();
        super.enable();
    }

    private void triggerAllAddedEvent() {
        for (SpotLight light : spotLights)
            getOwner().triggerOnRoot(new LightAddedEvent(light));
    }

    @Override
    public void disable() {
        if (isEnabled()) triggerAllRemovedEvent();
        super.disable();
    }

    private void triggerAllRemovedEvent() {
        for (SpotLight light : spotLights)
            getOwner().triggerOnRoot(new LightRemovedEvent(light));
    }

}
