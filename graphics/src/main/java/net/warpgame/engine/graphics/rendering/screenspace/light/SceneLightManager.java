package net.warpgame.engine.graphics.rendering.screenspace.light;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Transforms;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 * Created 2017-11-18 at 17
 */

@Service
@Profile("graphics")
public class SceneLightManager {
    private List<LightSourceProperty> lightProperties;

    public SceneLightManager() {
        this.lightProperties = new ArrayList<>();
    }

    private List<Vector3f> positions = new ArrayList<>();

    public List<Vector3f> getLightPositions() {
        for (int i = positions.size(); i < lightProperties.size(); i++) {
            positions.add(new Vector3f());
        }
        for (int i = 0; i < lightProperties.size(); i++) {
            LightSourceProperty property = lightProperties.get(i);
            Vector3f position = positions.get(i);
            Transforms.getAbsolutePosition(property.getOwner(), position); //TODO As always, positioning.
        }
        return positions;
    }


    public void addLight(LightSourceProperty lightSourceProperty) {
        lightProperties.add(lightSourceProperty);
    }

    public void removeLight(LightSourceProperty lightSourceProperty) {
        lightProperties.remove(lightSourceProperty);
    }

    public List<LightSourceProperty> getLightProperties() {
        return lightProperties;
    }
}
