package pl.warp.engine.graphics.framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.utility.BufferTools;


import static org.lwjgl.opengl.GL30.*;

/**
 * @author Jaca777
 *         Created 07.04.15 at 19:55
 */
public class MultisampleFramebuffer extends TextureFramebuffer {

    public MultisampleFramebuffer(MultisampleTexture2D destTex) {
        super(destTex, GL30.glGenFramebuffers(), GL30.glGenRenderbuffers());
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.name);
        attachDepthBuffer(destTex);
        attachTexture(destTex);
        int status = glCheckFramebufferStatus(GL_DRAW_FRAMEBUFFER);
        if(status == GL30.GL_FRAMEBUFFER_UNSUPPORTED) throw new FramebufferException("Frame buffers not supported");
        if(status != GL30.GL_FRAMEBUFFER_COMPLETE) throw new FramebufferException("Incomplete framebuffer: " + status);
    }

    private void attachDepthBuffer(MultisampleTexture2D destTex) {
        glBindRenderbuffer(GL_RENDERBUFFER, this.depthBuff);
        GL30.glRenderbufferStorageMultisample(GL_RENDERBUFFER, destTex.getSamples(), GL_DEPTH_COMPONENT32F, destTex.getWidth(), destTex.getHeight());
        GL30.glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.depthBuff);
    }

    private void attachTexture(MultisampleTexture2D texture) {
        GL20.glDrawBuffers(BufferTools.toDirectBuffer(new int[]{GL_COLOR_ATTACHMENT0}));
        GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getTexture());
        GL30.glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL32.GL_TEXTURE_2D_MULTISAMPLE, this.destTex.getTexture(), 0);
    }

}
