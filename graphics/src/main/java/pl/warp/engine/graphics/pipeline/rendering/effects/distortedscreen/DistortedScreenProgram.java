package pl.warp.engine.graphics.pipeline.rendering.effects.distortedscreen;

import pl.warp.engine.core.execution.task.update.Updatable;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 21
 */
public class DistortedScreenProgram extends Program implements Updatable {
    private static final String PROGRAM_PATH = "pl/warp/engine/graphics/pipeline/rendering/effects/";
    private static final String VERTEX_SHADER = "distortedscreen/vert";
    private static final String FRAGMENT_SHADER = "distortedscreen/frag";

    private int unifTime;

    private int time = 0;

    public DistortedScreenProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifTime = getUniformLocation("globalTime");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, 0);
    }

    @Override
    public void update(int delta) {
        this.time += delta;
        use();
        setUniformi(unifTime, time);
    }
}
