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

    public RenderingConfig(int fps, int width, int height) {
        this.fps = fps;
        this.width = width;
        this.height = height;
    }

    public int getFps() {
        return fps;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRenderingSamples() {
        return renderingSamples;
    }

    public RenderingConfig setRenderingSamples(int renderingSamples) {
        this.renderingSamples = renderingSamples;
        return this;
    }

    public RenderingConfig setBloomIterations(int bloomIterations) {
        this.bloomIterations = bloomIterations;
        return this;
    }

    public int getBloomIterations() {
        return bloomIterations;
    }
}
