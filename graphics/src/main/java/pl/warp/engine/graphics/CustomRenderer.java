package pl.warp.engine.graphics;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 16
 */
public abstract class CustomRenderer implements Renderer {

    private boolean enabled = false;

    public CustomRenderer(Graphics graphics) {
        graphics.getCustomRenderersManager().registerRenderer(this);
        setMainViewCamera(graphics.getMainViewCamera());
        Display display = graphics.getConfig().getDisplay();
        resize(display.getWidth(), display.getHeight());
    }

    public void resize(int newWidth, int newHeight){

    }

    public void setMainViewCamera(Camera camera){

    }

    void enable() {
        enabled = true;
    }

    boolean isEnabled() {
        return enabled;
    }
}
