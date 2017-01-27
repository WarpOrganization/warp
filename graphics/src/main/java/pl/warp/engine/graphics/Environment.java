package pl.warp.engine.graphics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.light.SpotLight;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class Environment {

    private boolean lightEnabled = true;
    private List<SpotLight> spotLights = new ArrayList<>();
    private List<Component> lensFlareComponents = new ArrayList<>();

    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    public void addSpotLightSource(SpotLight spotLightSource) {
        spotLights.add(spotLightSource);
    }

    public void removeSpotLightSource(SpotLight light) {
        if (!spotLights.remove(light))
            throw new RendererException("Unable to remove spot light: " +
                    "Given light is not present in the environment");
    }

    public boolean isLightEnabled() {
        return lightEnabled;
    }

    public void setLightEnabled(boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }

    public List<Component> getLensFlareComponents() {
        return lensFlareComponents;
    }

    public void addLensFlareComponent(Component component) {
        lensFlareComponents.add(component);
    }

    public void resetLensFlareComponents() {
        lensFlareComponents.clear();
    }
}
