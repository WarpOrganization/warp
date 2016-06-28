package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.texture.Texture;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.utility.BufferTools;

import static org.lwjgl.opengl.GL11.GL_READ_BUFFER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
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

    public void unload() {
        GL30.glDeleteFramebuffers(this.name);
    }

    public static final Framebuffer SCREEN_FRAMEBUFFER = new Framebuffer(0);
}
