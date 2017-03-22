package pl.warp.engine.graphics.pipeline.rendering;

import org.joml.Matrix3f;
import pl.warp.engine.graphics.program.Program;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 12
 */
public abstract class CubemapRenderingProgram extends Program {
    abstract void useMatrices(Matrix3f[] matrices);
}
