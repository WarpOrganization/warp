package pl.warp.engine.graphics.rendering.scene.gbuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.framebuffer.FramebufferException;

/**
 * @author Jaca777
 * Created 2017-10-31 at 22
 */

public class GBufferFramebuffer extends Framebuffer {

    private GBuffer gBuffer;
    private boolean complete = false;

    public GBufferFramebuffer(GBuffer gBuffer) {
        super(GL30.glGenFramebuffers());
        this.gBuffer = gBuffer;
        init();
    }

    private void init() {
        bindDraw();
        GL32.glFramebufferTexture(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, gBuffer.getTextureName(3), 0);
        for(int i = 0; i < 3; i++) {
            int offset = GL30.GL_COLOR_ATTACHMENT0 + i;
            GL32.glFramebufferTexture(GL30.GL_DRAW_FRAMEBUFFER, offset, gBuffer.getTextureName(i), 0);
        }
        int buffers[] = {GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_COLOR_ATTACHMENT2};
        GL20.glDrawBuffers(buffers);
        int status = GL30.glCheckFramebufferStatus(GL30.GL_DRAW_FRAMEBUFFER);
        if(status == GL30.GL_FRAMEBUFFER_COMPLETE) complete = true;
        else throw new FramebufferException("Failed to assemble framebuffer: " + getName() + ". Status is: " + status);
    }

    @Override
    public int getWidth() {
        return gBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return gBuffer.getHeight();
    }

    @Override
    public void clean() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
}
