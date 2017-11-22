package pl.warp.engine.graphics.rendering.screenspace.light;

import pl.warp.engine.core.context.service.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 * Created 2017-11-18 at 17
 */

@Service
public class SceneLightManager {
    private List<LightSourceProperty> lightProperties;

    public SceneLightManager() {
        this.lightProperties = new ArrayList<>();
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
