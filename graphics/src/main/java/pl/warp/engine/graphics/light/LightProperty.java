package pl.warp.engine.graphics.light;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.light.DirectionalSpotLight;
import pl.warp.engine.graphics.light.LightAddedEvent;
import pl.warp.engine.graphics.light.LightRemovedEvent;
import pl.warp.engine.graphics.light.SpotLight;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hubertus on 2016-06-26.
 */

public class LightProperty extends Property<Component> {

    private Set<DirectionalSpotLight> directionalSpotLights = new HashSet<>();
    private Set<SpotLight> spotLights = new HashSet<>();

    public static final String LIGHT_PROPERTY_NAME = "lightProperty";

    public LightProperty(Component owner, Set<DirectionalSpotLight> directionalSpotLights, Set<SpotLight> spotLights) {
        super(owner);
        this.directionalSpotLights = directionalSpotLights;
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

    public void addDirectionalSpotLight(DirectionalSpotLight light) {
        this.directionalSpotLights.add(light);
        getOwner().triggerOnRoot(new LightAddedEvent(light));
    }


    public Set<DirectionalSpotLight> getDirectionalSpotLights() {
        return directionalSpotLights;
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
        getOwner().triggerOnRoot(new LightAddedEvent(directionalSpotLights.toArray(new DirectionalSpotLight[0]), spotLights.toArray(new SpotLight[0])));
    }

    @Override
    public void disable() {
        if (isEnabled()) triggerAllRemovedEvent();
        super.disable();
    }

    private void triggerAllRemovedEvent() {
        getOwner().triggerOnRoot(new LightRemovedEvent(directionalSpotLights.toArray(new DirectionalSpotLight[0]), spotLights.toArray(new SpotLight[0])));
    }

}
