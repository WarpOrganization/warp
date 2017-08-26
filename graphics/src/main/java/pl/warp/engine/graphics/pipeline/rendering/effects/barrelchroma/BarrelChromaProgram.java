package pl.warp.engine.graphics.pipeline.rendering.effects.barrelchroma;

import pl.warp.engine.core.execution.task.update.Updatable;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 00
 */
public class BarrelChromaProgram extends Program implements Updatable {

    private static final String PROGRAM_PATH = "pl/warp/engine/graphics/pipeline/rendering/effects/";
    private static final String VERTEX_SHADER = "barrelchroma/vert";
    private static final String FRAGMENT_SHADER = "barrelchroma/frag";

    private int unifWidth;
    private int unifHeight;
    private int unifMaxDistortPx;
    private int unifTime;

    private int time;

    public BarrelChromaProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifWidth = getUniformLocation("resx");
        this.unifHeight = getUniformLocation("resy");
        this.unifMaxDistortPx = getUniformLocation("max_distort_px");
        this.unifTime = getUniformLocation("globalTime");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, 0);
    }

    public void useResolution(int width, int height) {
        setUniformf(unifWidth, width);
        setUniformf(unifHeight, height);
    }

    public void useMaxDistortPx(int px) {
        setUniformf(unifMaxDistortPx, px);
    }

    @Override
    public void update(int delta) {
        this.time += delta;
        use();
        setUniformi(this.unifTime, this.time);
    }
}
