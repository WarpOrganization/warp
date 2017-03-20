package pl.warp.engine.graphics.pipeline.rendering;

import pl.warp.engine.graphics.pipeline.Flow;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public abstract class ProgramTextureFlow<T extends Program> extends ProgramTextureSource<T> implements Flow<Texture2D, Texture2D> {

    private Texture2D input;

    public ProgramTextureFlow(T program) {
        super(program);
    }

    @Override
    public void setInput(Texture2D input) {
       this.input = input;
    }

    protected Texture2D getInput() {
        return input;
    }


}
