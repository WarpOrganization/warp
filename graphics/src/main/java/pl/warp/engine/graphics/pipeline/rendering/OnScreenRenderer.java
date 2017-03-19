package pl.warp.engine.graphics.pipeline.rendering;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.pipeline.Sink;
import pl.warp.engine.graphics.program.rendering.identity.IdentityProgram;
import pl.warp.engine.graphics.texture.Texture2D;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 23
 */
public class OnScreenRenderer implements Sink<Texture2D> {

    private RenderingConfig config;
    private Texture2D srcTexture;
    private IdentityProgram identityProgram;
    private Quad quad;

    public OnScreenRenderer(RenderingConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        this.identityProgram = new IdentityProgram();
        this.identityProgram.setExposure(config.getExposure());
        this.quad = new Quad();
    }

    @Override
    public void destroy() {
        quad.destroy();
    }

    @Override
    public void setInput(Texture2D input) {
        this.srcTexture = input;
    }

    @Override
    public void update() {
        Framebuffer.SCREEN_FRAMEBUFFER.bindDraw();
        Framebuffer.SCREEN_FRAMEBUFFER.clean();
        identityProgram.use();
        identityProgram.useTexture(srcTexture);
        quad.bind();
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Quad.INDICES_NUMBER, GL11.GL_UNSIGNED_INT, 0);
        quad.unbind();
        GL11.glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        //do nothing
    }
}
