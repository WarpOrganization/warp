package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.math.BufferTools;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.texture.TextureShape2D;

import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 17
 */
public class MultisampleDepthTextureFramebuffer extends Framebuffer{

    protected TextureShape2D depthTex;
    protected TextureShape2D destTex;

    public MultisampleDepthTextureFramebuffer(MultisampleTexture2D destTex, MultisampleTexture2D depthTex) {
        super(GL30.glGenFramebuffers());
        this.destTex = destTex;
        this.depthTex = depthTex;
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.name);
        attachDepthTexture(depthTex);
        attachTexture(destTex);
        int status = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
        if(status == GL30.GL_FRAMEBUFFER_UNSUPPORTED) throw new FramebufferException("Frame buffers not supported");
        if(status != GL30.GL_FRAMEBUFFER_COMPLETE) throw new FramebufferException("Incomplete framebuffer: " + status);
    }

    private void attachDepthTexture(MultisampleTexture2D texture) {
        GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getTexture());
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getTexture(), 0);
    }

    private void attachTexture(MultisampleTexture2D texture) {
        GL20.glDrawBuffers(BufferTools.toDirectBuffer(new int[]{GL_COLOR_ATTACHMENT0}));
        GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getTexture());
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getTexture(), 0);
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
    public int getWidth() {
        return destTex.getWidth();
    }

    @Override
    public int getHeight() {
        return destTex.getHeight();
    }

    @Override
    public boolean isAssembled() {
        return true;
    }
}
