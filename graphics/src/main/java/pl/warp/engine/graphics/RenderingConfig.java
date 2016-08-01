package pl.warp.engine.graphics;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class RenderingConfig {

    private int fps;
    private int displayWidth, displayHeight;
    private int renderingSamples = 5;
    private int bloomIterations = 5;
    private float exposure = 3f;
    private float bloomLevel = 1f;
    private float bloomThreshold = 1.0f;

    public RenderingConfig(int fps, int displayWidth, int displayHeight) {
        this.fps = fps;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
    }

    public int getFps() {
        return fps;
    }

    public RenderingConfig setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public RenderingConfig setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
        return this;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public RenderingConfig setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
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
}
