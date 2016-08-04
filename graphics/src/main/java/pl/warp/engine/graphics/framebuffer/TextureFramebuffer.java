package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.texture.Texture;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.TextureShape2D;
import pl.warp.engine.graphics.utility.BufferTools;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_DEPTH_COMPONENT32F;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 16
 */
public class TextureFramebuffer extends Framebuffer {

    protected int depthBuff;
    protected TextureShape2D destTex;

    public TextureFramebuffer(TextureShape2D destTex) {
        super(GL30.glGenFramebuffers());
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, name);

        this.depthBuff = GL30.glGenRenderbuffers();
        attachDepthBuffer(destTex.getWidth(), destTex.getHeight());

        this.destTex = destTex;
        attachTexture();

        int status = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
        if (status != GL30.GL_FRAMEBUFFER_COMPLETE) throw new FramebufferException("Incomplete framebuffer: " + status);
    }

    private void attachDepthBuffer(int width, int height) {
        glBindRenderbuffer(GL_RENDERBUFFER, this.depthBuff);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, width, height);
    }

    private void attachTexture() {
        GL20.glDrawBuffers(BufferTools.toDirectBuffer(new int[]{GL_COLOR_ATTACHMENT0}));
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.destTex.getTexture(), 0);
        GL30.glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.depthBuff);
    }

    protected TextureFramebuffer(TextureShape2D destTex, int name, int depthBuff) {
        super(name);
        this.depthBuff = depthBuff;
        this.destTex = destTex;
    }

    protected TextureFramebuffer(TextureShape2D destTex, int name) {
        super(name);
        this.depthBuff = -1;
        this.destTex = destTex;
    }

    public void resize(int w, int h) {
        if (depthBuff != -1)
            attachDepthBuffer(w, h);
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
        GL30.glDeleteRenderbuffers(this.depthBuff);
        destTex.delete();
    }
}
