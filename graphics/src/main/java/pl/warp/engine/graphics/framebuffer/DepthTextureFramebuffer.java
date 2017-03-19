package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.math.BufferTools;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.TextureShape2D;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 17
 */
public class DepthTextureFramebuffer extends Framebuffer {

    protected TextureShape2D depthTex;
    protected TextureShape2D destTex;

    public DepthTextureFramebuffer(TextureShape2D destTex, Texture2D depthTex) {
        super(GL30.glGenFramebuffers());
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, name);

        this.depthTex = depthTex;
        attachDepthTexture();

        this.destTex = destTex;
        attachTexture();

        int status = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
        if (status != GL30.GL_FRAMEBUFFER_COMPLETE) throw new FramebufferException("Incomplete framebuffer: " + status);
    }

    private void attachDepthTexture() {
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, this.depthTex.getTexture(), 0);
    }

    private void attachTexture() {
        GL20.glDrawBuffers(BufferTools.toDirectBuffer(new int[]{GL_COLOR_ATTACHMENT0}));
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.destTex.getTexture(), 0);
    }

    public void resize(int w, int h) {
        depthTex.resize(w, h);
        destTex.resize(w, h);
    }

    public void bindDraw() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, name);
    }

    public void bindRead() {
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, name);
    }

    public TextureShape2D getDestTex() {
        return destTex;
    }

    public int getName() {
        return name;
    }

    public void delete() {
        super.delete();
        depthTex.delete();
        destTex.delete();
    }

    @Override
    public boolean isAssembled() {
        return true;
    }
}
