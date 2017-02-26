package pl.warp.engine.graphics.postprocessing.sunshaft;

import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 21
 */
public class SunshaftSource {
    private Component source;

    private float exposure = 0.2f;
    private float decay = 0.98f;
    private float density = 1.0f;
    private float weight = 0.4f;
    private float clamp = 2.0f;

    public Component getSource() {
        return source;
    }

    public void setComponent(Component source) {
        this.source = source;
    }

    public float getExposure() {
        return exposure;
    }

    public void setExposure(float exposure) {
        this.exposure = exposure;
    }

    public float getDecay() {
        return decay;
    }

    public void setDecay(float decay) {
        this.decay = decay;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getClamp() {
        return clamp;
    }

    public void setClamp(float clamp) {
        this.clamp = clamp;
    }
}
