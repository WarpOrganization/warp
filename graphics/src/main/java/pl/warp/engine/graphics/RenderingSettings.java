package pl.warp.engine.graphics;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class RenderingSettings {

    private int fps;
    private int width, height;
    private int renderingSamples = 4;

    public RenderingSettings(int fps, int width, int height) {
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

    public RenderingSettings setRenderingSamples(int renderingSamples) {
        this.renderingSamples = renderingSamples;
        return this;
    }
}
