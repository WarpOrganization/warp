package pl.warp.engine.graphics.postprocessing;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.pipeline.Sink;
import pl.warp.engine.graphics.shader.program.identitymultisample.IdentityMultisampleProgram;
import pl.warp.engine.graphics.shader.program.hdr.HDRProgram;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 12
 */
public class HDROnScreenRenderer implements Sink<BloomRendererOutput> {

    private BloomRendererOutput src;
    private HDRProgram hdrProgram;
    private Quad rect;

    @Override
    public void init() {
        this.hdrProgram = new HDRProgram();
        this.rect = new Quad(IdentityMultisampleProgram.ATTR_VERTEX, IdentityMultisampleProgram.ATTR_TEX_COORD);
    }

    @Override
    public void destroy() {
        rect.destroy();
    }

    @Override
    public void setInput(BloomRendererOutput input) {
        this.src = input;
    }

    @Override
    public void update(int delta) {
        Framebuffer.SCREEN_FRAMEBUFFER.bindDraw();
        Framebuffer.SCREEN_FRAMEBUFFER.clean();
        hdrProgram.use();
        hdrProgram.useTexture(src.getScene());
        rect.bind();
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glDrawElements(GL11.GL_TRIANGLES, Quad.INDICES_NUMBER, GL11.GL_UNSIGNED_INT, 0);
        rect.unbind();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        //do nothing
    }
}
