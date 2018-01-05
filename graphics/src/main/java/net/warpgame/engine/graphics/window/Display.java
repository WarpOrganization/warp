package net.warpgame.engine.graphics.window;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 21
 */
public class Display {

    private boolean fullscreen;
    private boolean visible = true;
    private boolean resizable = false;
    private int width;
    private int height;

    public Display() {
    }

    public Display(boolean fullscreen, int width, int height) {
        this.fullscreen = fullscreen;
        this.width = width;
        this.height = height;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
