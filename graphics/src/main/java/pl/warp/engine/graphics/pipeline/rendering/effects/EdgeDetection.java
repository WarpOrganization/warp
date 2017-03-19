package pl.warp.engine.graphics.pipeline.rendering.effects;

import pl.warp.engine.graphics.pipeline.rendering.ProgramTextureFlow;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public class EdgeDetection extends ProgramTextureFlow {
    public EdgeDetection(Program program, Texture2D outputTexture) {
        super(program, outputTexture);
    }
/*    public EdgeDetection(Texture2D outputTexture, EngineContext context) {
        super( outputTexture);
    }*/

    @Override
    public void setInput(Object input) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareProgram(Program program) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
