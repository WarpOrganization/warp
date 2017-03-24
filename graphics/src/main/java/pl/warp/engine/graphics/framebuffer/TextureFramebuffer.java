package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.math.BufferTools;
import pl.warp.engine.graphics.texture.TextureShape2D;

import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 16
 */
public class TextureFramebuffer extends Framebuffer {

    protected int depthBuff;
    protected TextureShape2D destTex;
    private boolean assembled = false;

    public TextureFramebuffer() {
        super(-1);
    }

    protected TextureFramebuffer(TextureShape2D destTex, int name, int depthBuff) {
        super(name);
        this.depthBuff = depthBuff;
        this.destTex = destTex;
    }

    public TextureFramebuffer(TextureShape2D destTex) {
        super(GL30.glGenFramebuffers());
        this.destTex = destTex;
        create();
    }

    public void create() {
        setName(GL30.glGenFramebuffers());
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, name);
        this.depthBuff = GL30.glGenRenderbuffers();
        assemble();
    }

    protected void assemble() {
        attachDepthBuffer();
        attachTexture();
        int status = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
        if (status != GL30.GL_FRAMEBUFFER_COMPLETE) throw new FramebufferException("Incomplete framebuffer: " + status);
        else assembled = true;
    }

    protected void attachDepthBuffer() {
        glBindRenderbuffer(GL_RENDERBUFFER, this.depthBuff);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, this.destTex.getWidth(), this.destTex.getHeight());
        GL30.glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.depthBuff);
    }

    protected void attachTexture() {
        GL20.glDrawBuffers(BufferTools.toDirectBuffer(new int[]{GL_COLOR_ATTACHMENT0}));
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, this.destTex.getType(), this.destTex.getTexture(), 0);
    }

    public void resize(int w, int h) {
        if (depthBuff != -1)
            attachDepthBuffer();
        destTex.resize(w, h);
    }
    public TextureShape2D getDestTex() {
        return destTex;
    }

    @Override
    public int getWidth() {
        return destTex.getWidth();
    }

    @Override
    public int getHeight() {
        return destTex.getHeight();
    }


    public void setDestinationTexture(TextureShape2D destTex) {
        this.destTex = destTex;
        if (assembled) assemble();
    }

    public void delete() {
        super.delete();
        GL30.glDeleteRenderbuffers(this.depthBuff);
        destTex.delete();
    }

    @Override
    public boolean isAssembled() {
        return assembled;
    }
}
