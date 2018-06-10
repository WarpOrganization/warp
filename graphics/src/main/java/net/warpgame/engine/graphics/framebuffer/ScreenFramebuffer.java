package net.warpgame.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL11;
import net.warpgame.engine.core.context.config.ConfigValue;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.window.Display;

/**
 * @author Jaca777
 * Created 2017-09-24 at 12
 */

@Service
@EnableConfig
public class ScreenFramebuffer extends Framebuffer {
    private int screenWidth;
    private int screenHeight;

    public ScreenFramebuffer() {
        super(0);
    }

    @Override
    public int getWidth() {
        return screenWidth;
    }

    @Override
    public int getHeight() {
        return screenHeight;
    }

    @Override
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @ConfigValue("graphics.display")
    public void onDisplayChanged(Display newDisplay) {
        this.screenWidth = newDisplay.getWidth();
        this.screenHeight = newDisplay.getHeight();
    }
}
