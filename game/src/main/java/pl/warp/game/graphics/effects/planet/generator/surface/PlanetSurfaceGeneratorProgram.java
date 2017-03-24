package pl.warp.game.graphics.effects.planet.generator.surface;

import org.joml.Matrix3f;
import pl.warp.engine.graphics.pipeline.rendering.CubemapRenderingProgram;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.ExternalProgramLoader;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public class PlanetSurfaceGeneratorProgram extends CubemapRenderingProgram {

    private static final String PROGRAM_PATH = "pl/warp/game/graphics/effects/planet/generator/";
    private static final String VERTEX_SHADER = "surface/vert";
    private static final String GEOMETRY_SHADER = "surface/geom";
    private static final String FRAGMENT_SHADER = "surface/frag";

    private int[] unifMatrices;
    private int unifProjMatrix;

    public PlanetSurfaceGeneratorProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, GEOMETRY_SHADER,
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD,
                        new ExternalProgramLoader(PROGRAM_PATH)));
    }

    @Override
    public void compile() {
        super.compile();
        this.unifMatrices = new int[6];
        for (int i = 0; i < 6; i++)
            this.unifMatrices[i] = getUniformLocation("matrices[" + i + "]");
        this.unifProjMatrix = getUniformLocation("projMatrix");
    }

    @Override
    public void useMatrices(Matrix3f[] matrices) {
        for (int i = 0; i < 6; i++)
            setUniformMatrix3(unifMatrices[i], matrices[i]);
    }


}
