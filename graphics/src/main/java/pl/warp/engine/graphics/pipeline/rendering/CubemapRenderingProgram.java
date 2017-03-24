package pl.warp.engine.graphics.pipeline.rendering;

import org.joml.Matrix3f;
import pl.warp.engine.graphics.program.GeometryProgram;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 12
 */
public abstract class CubemapRenderingProgram extends GeometryProgram {


    public CubemapRenderingProgram(String vertexShaderName, String fragmentShaderName, String geometryShaderName, ExtendedGLSLProgramCompiler compiler) {
        super(vertexShaderName, fragmentShaderName, geometryShaderName, compiler);
    }

    public CubemapRenderingProgram(String programName) {
        super(programName);
    }

    public abstract void useMatrices(Matrix3f[] matrices);
}
