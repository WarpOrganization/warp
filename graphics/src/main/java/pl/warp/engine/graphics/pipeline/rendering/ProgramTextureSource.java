package pl.warp.engine.graphics.pipeline.rendering;

import pl.warp.engine.graphics.framebuffer.TextureFramebuffer;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 21
 */
public abstract class ProgramTextureSource<T extends Program> extends FramebufferPipelineElement implements Source<Texture2D>  {

    private T program;
    private Texture2D outputTexture;
    private TextureFramebuffer framebuffer;

    public ProgramTextureSource(T program, Texture2D outputTexture) {
        super(new TextureFramebuffer());
        this.program = program;
        this.outputTexture = outputTexture;
    }

    @Override
    void prepare() {
        program.use();
        prepareProgram(program);
    }

    protected abstract void prepareProgram(T program);

    @Override
    public void init() {
        this.framebuffer = (TextureFramebuffer) super.framebuffer;
        this.framebuffer.setDestinationTexture(outputTexture);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onResize(int newWidth, int newHeight) {

    }

    @Override
    public Texture2D getOutput() {
      return outputTexture;
    }
}
