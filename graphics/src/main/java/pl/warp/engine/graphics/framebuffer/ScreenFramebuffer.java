package pl.warp.engine.graphics.framebuffer;

import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-03-25 at 00
 */
public class ScreenFramebuffer extends Framebuffer {

    private Display display;

    public ScreenFramebuffer(Graphics graphics) {
        super(0);
        this.display = graphics.getConfig().getDisplay();
    }

    @Override
    public int getWidth() {
        return display.getWidth();
    }

    @Override
    public int getHeight() {
        return display.getHeight();
    }

    @Override
    public boolean isAssembled() {
        return true;
    }
}
