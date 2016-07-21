package pl.warp.engine.graphics;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class RenderingConfig {

    private int fps;
    private int width, height;
    private int renderingSamples = 4;
    private int bloomIterations = 5;
    private float exposure = 1.2f;
    private float bloomLevel = 1.0f;
    private float bloomThreshold = 0.7f;

    public RenderingConfig(int fps, int width, int height) {
        this.fps = fps;
        this.width = width;
        this.height = height;
    }

    public int getFps() {
        return fps;
    }

    public RenderingConfig setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public RenderingConfig setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public RenderingConfig setHeight(int height) {
        this.height = height;
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
