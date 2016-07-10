package pl.warp.engine.graphics.light;

import pl.warp.engine.graphics.RendererException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class LightEnvironment {

    private boolean lightEnabled = true;
    private List<SpotLight> spotLights = new ArrayList<>();

    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    public void addSpotLightSource(SpotLight spotLightSource) {
        spotLights.add(spotLightSource);
    }

    public void removeSpotLightSource(SpotLight light) {
        if(!spotLights.remove(light))
            throw new RendererException("Unable to remove spot light: " +
                    "Given light is not present in the environment");
    }

    public boolean isLightEnabled() {
        return lightEnabled;
    }

    public void setLightEnabled(boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }
}
