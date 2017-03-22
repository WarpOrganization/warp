package pl.warp.engine.graphics.pipeline.rendering;

import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.TextureShape2D;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public abstract class ProgramTextureFlow<T extends Program, R extends TextureShape2D> extends ProgramTextureSource<T, R> implements Flow<R, R> {

    private R input;

    public ProgramTextureFlow(T program) {
        super(program);
    }

    @Override
    public void setInput(R input) {
       this.input = input;
    }

    protected R getInput() {
        return input;
    }


}
