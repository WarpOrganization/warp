package pl.warp.engine.graphics.pipeline.rendering.effects.mosaic;

import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 17
 */
public class MosaicEffectProgram extends Program {
    private static final String PROGRAM_PATH = "pl/warp/engine/graphics/pipeline/rendering/effects/";
    private static final String VERTEX_SHADER = "mosaic/vert";
    private static final String FRAGMENT_SHADER = "mosaic/frag";

    private int unifWidth;
    private int unifHeight;
    private int unifTileRadius;
    private int unifInnerTileRadius;

    public MosaicEffectProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifWidth = getUniformLocation("width");
        this.unifHeight = getUniformLocation("height");
        this.unifTileRadius = getUniformLocation("tileRadius");
        this.unifInnerTileRadius = getUniformLocation("innerTileRadius");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, 0);
    }

    public void useDimensions(int width, int height) {
        setUniformf(unifWidth, width);
        setUniformf(unifHeight, height);
    }

    public void useTileRadius(float radius, float innerRadius) {
        setUniformf(unifTileRadius, radius);
        setUniformf(unifInnerTileRadius, innerRadius);
    }
}
