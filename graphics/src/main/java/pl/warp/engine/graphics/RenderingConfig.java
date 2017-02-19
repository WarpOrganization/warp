package pl.warp.engine.graphics;

import javafx.beans.property.ReadOnlyIntegerProperty;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class RenderingConfig {

    private int fps;
    private Display display;
    private int renderingSamples = 5;
    private int bloomIterations = 5;
    private float exposure = 1.4f;
    private boolean bloom = true;
    private boolean lens = true;
    private float bloomLevel = 1f;
    private float bloomThreshold = 1.0f;
    private float fov = 70;
    private int scene;

    public RenderingConfig(int fps, Display display) {
        this.fps = fps;
        this.display = display;
    }

    public int getFps() {
        return fps;
    }

    public RenderingConfig setFps(int fps) {
        this.fps = fps;
        return this;
    }


    public int getRenderingSamples() {
        return renderingSamples;
    }

    public RenderingConfig setRenderingSamples(int renderingSamples) {
        this.renderingSamples = renderingSamples;
        return this;
    }

    public int getBloomIterations() {
        return bloomIterations;
    }

    public RenderingConfig setBloomIterations(int bloomIterations) {
        this.bloomIterations = bloomIterations;
        return this;
    }

    public float getExposure() {
        return exposure;
    }

    public RenderingConfig setExposure(float exposure) {
        this.exposure = exposure;
        return this;
    }

    public float getBloomLevel() {
        return bloomLevel;
    }

    public RenderingConfig setBloomLevel(float bloomLevel) {
        this.bloomLevel = bloomLevel;
        return this;
    }

    public float getBloomThreshold() {
        return bloomThreshold;
    }

    public RenderingConfig setBloomThreshold(float bloomThreshold) {
        this.bloomThreshold = bloomThreshold;
        return this;
    }

    public Display getDisplay() {
        return display;
    }

    public boolean isBloomEnabled() {
        return bloom;
    }

    public void setBloom(boolean bloom) {
        this.bloom = bloom;
    }

    public boolean areLensEnabled() {
        return lens;
    }

    public void setLens(boolean lens) {
        this.lens = lens;
    }

    public float getFov() {
        return fov;
    }

    public RenderingConfig setFov(float fov) {
        this.fov = fov;
        return this;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public int getScene() {
        return scene;
    }
}
