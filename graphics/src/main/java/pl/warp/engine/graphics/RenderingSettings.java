package pl.warp.engine.graphics;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class RenderingSettings {
    private boolean changed = false;
    private int width, height;
    private int renderingSamples = 4;

    public RenderingSettings(int width, int height) {
        this.width = width;
        this.height = height;
    }

    void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean hasChanged() {
        return changed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        setChanged(true);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        setChanged(true);
    }

    public int getRenderingSamples() {
        return renderingSamples;
    }

    public void setRenderingSamples(int renderingSamples) {
        this.renderingSamples = renderingSamples;
        setChanged(true);
    }
}
