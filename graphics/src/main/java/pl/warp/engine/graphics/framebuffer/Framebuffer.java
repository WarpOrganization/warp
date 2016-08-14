package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 06.04.15 at 15:21
 */
public class Framebuffer {

    protected int name;

    public Framebuffer(int name) {
        this.name = name;
    }

    public void bindDraw() {
        GL30.glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    public void bindRead() {
        GL30.glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    public int getName() {
        return name;
    }

    public void delete() {
        GL30.glDeleteFramebuffers(this.name);
    }

    public static final Framebuffer SCREEN_FRAMEBUFFER = new Framebuffer(0);

    public void clean() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }
}
