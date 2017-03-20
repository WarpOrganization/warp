package pl.warp.engine.graphics.pipeline.rendering.effects.edgedetection;

import org.joml.Vector3f;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 00
 */
public class EdgeDetectionProgram extends Program {

    private static final String PROGRAM_PATH = "pl/warp/engine/graphics/pipeline/rendering/effects/";
    private static final String VERTEX_SHADER = "edgedetection/vert";
    private static final String FRAGMENT_SHADER = "edgedetection/frag";

    private int unifEdgeColor;
    private int unifWidth;
    private int unifHeight;

    public EdgeDetectionProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifEdgeColor = getUniformLocation("edgeColor");
        this.unifWidth = getUniformLocation("width");
        this.unifHeight = getUniformLocation("height");
    }

    public void useTexture(Texture2D texture){
        useTexture(texture, 0);
    }

    public void useEdgeColor(Vector3f color){
        setUniformV3(unifEdgeColor, color);
    }

    public void useDimensions(int width, int height){
        setUniformf(unifWidth, width);
        setUniformf(unifHeight, height);
    }
}
