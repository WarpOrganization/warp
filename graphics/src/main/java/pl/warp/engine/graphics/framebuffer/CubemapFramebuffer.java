package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.*;
import pl.warp.engine.graphics.GLErrors;
import pl.warp.engine.graphics.utility.BufferTools;
import pl.warp.engine.graphics.texture.Cubemap;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 22
 */
public class CubemapFramebuffer extends TextureFramebuffer {

    public CubemapFramebuffer() {
        super();
    }

    public CubemapFramebuffer(Cubemap destCubemap) {
        super(destCubemap);
    }

    @Override
    protected void attachDepthBuffer() {
        // do nothing
    }

    @Override
    protected void attachTexture() {
        GL20.glDrawBuffers(BufferTools.toDirectBuffer(new int[]{GL_COLOR_ATTACHMENT0}));
        destTex.bind();
        GL32.glFramebufferTexture(GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, destTex.getTexture(), 0);
        GLErrors.checkOGLErrors();
    }

}
