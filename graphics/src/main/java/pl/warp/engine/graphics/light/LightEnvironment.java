package pl.warp.engine.graphics.light;

import pl.warp.engine.graphics.RendererException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class LightEnvironment {
    private List<DirectionalSpotLight> directionalSpotLights = new ArrayList<>();
    private List<SpotLight> spotLights = new ArrayList<>();

    public List<DirectionalSpotLight> getDirectionalSpotLights() {
        return directionalSpotLights;
    }

    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    public void addDirectionalLightSource(DirectionalSpotLight directionalSpotLight) {
        directionalSpotLights.add(directionalSpotLight);
    }

    public void removeDirectionalLightSource(DirectionalSpotLight light) {
        if(!directionalSpotLights.remove(light))
            throw new RendererException("Unable to remove directional light: " +
                    "Given light is not present in the environment");
    }

    public void addSpotLightSource(SpotLight directionalSpotLight) {
        spotLights.add(directionalSpotLight);
    }

    public void removeSpotLightSource(SpotLight light) {
        if(!spotLights.remove(light))
            throw new RendererException("Unable to remove directional light: " +
                    "Given light is not present in the environment");
    }
}
