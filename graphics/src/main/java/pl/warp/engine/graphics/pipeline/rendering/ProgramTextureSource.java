package pl.warp.engine.graphics.pipeline.rendering;

import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.framebuffer.Framebuffer;
import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.TextureShape2D;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 21
 */
public abstract class ProgramTextureSource<T extends Program, R extends TextureShape2D> extends FramebufferPipelineElement implements Source<R>  {

    private T program;
    private TextureFramebuffer framebuffer;

    public ProgramTextureSource(T program) {
        super(new TextureFramebuffer());
        this.program = program;
    }

    public ProgramTextureSource(Framebuffer framebuffer, T program) {
        super(framebuffer);

        this.program = program;
    }

    @Override
    void prepare() {
        program.use();
        prepareProgram(program);
    }

    protected abstract void prepareProgram(T program);

    @Override
    public void init(Graphics graphics) {
        super.init(graphics);
        this.program.compile();
        this.framebuffer = (TextureFramebuffer) super.framebuffer;
        this.framebuffer.setDestinationTexture(getOutput());
        this.framebuffer.create();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onResize(int newWidth, int newHeight) {

    }

    protected T getProgram() {
        return program;
    }
}
