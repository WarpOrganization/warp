package pl.warp.engine.graphics.pipeline.rendering.effects.monochromatic;

import org.joml.Vector3f;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 22
 */
public class MonochromaticProgram extends Program {
    private static final String PROGRAM_PATH = "pl/warp/engine/graphics/pipeline/rendering/effects/";
    private static final String VERTEX_SHADER = "monochromatic/vert";
    private static final String FRAGMENT_SHADER = "monochromatic/frag";

    private int unifColor;

    public MonochromaticProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifColor = getUniformLocation("color");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, 0);
    }

    public void useColor(Vector3f color){
        setUniformV3(this.unifColor, color);
    }

}
