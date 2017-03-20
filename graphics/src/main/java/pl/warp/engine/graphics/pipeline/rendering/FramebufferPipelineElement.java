package pl.warp.engine.graphics.pipeline.rendering;

import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.mesh.Quad;
import pl.warp.engine.graphics.pipeline.PipelineElement;

/**
 * @author Jaca777
 *         Created 2017-03-16 at 21
 */
public abstract class FramebufferPipelineElement implements PipelineElement {

    protected Framebuffer framebuffer;
    private Quad quad;

    public FramebufferPipelineElement(Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public void update() {
        framebuffer.bindDraw();
        framebuffer.clean();
        prepare();
        quad.bind();
        quad.draw();
        quad.unbind();
    }

    abstract void prepare();

    @Override
    public void init(Graphics graphics) {
        this.quad = new Quad();
    }

    @Override
    public void destroy() {
        quad.destroy();
    }

}
