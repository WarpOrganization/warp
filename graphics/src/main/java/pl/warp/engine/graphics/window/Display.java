package pl.warp.engine.graphics.window;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 21
 */
public class Display {
    private boolean visible = true;
    private boolean resizable = false;
    private int width;
    private int height;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public Display setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean isResizable() {
        return resizable;
    }

    public Display setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Display setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Display setHeight(int height) {
        this.height = height;
        return this;
    }
}
