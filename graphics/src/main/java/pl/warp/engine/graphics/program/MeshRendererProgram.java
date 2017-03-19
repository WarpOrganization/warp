package pl.warp.engine.graphics.program;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public abstract class MeshRendererProgram extends Program {

    public static final int MAX_SPOT_LIGHT_SOURCES = 25;

    public static final int SPOT_LIGHT_POSITION = 0;
    public static final int SPOT_LIGHT_CONE_DIRECTION = 1;
    public static final int SPOT_LIGHT_CONE_ANGLE = 2;
    public static final int SPOT_LIGHT_CONE_GRADIENT = 3;
    public static final int SPOT_LIGHT_COLOR = 4;
    public static final int SPOT_LIGHT_AMBIENT_COLOR = 5;
    public static final int SPOT_LIGHT_ATTENUATION = 6;
    public static final int SPOT_LIGHT_GRADIENT = 7;

    public static final int[] ATTRIBUTES = {0, 1, 2}; //vertex, textureCoord, normal
    public static final int ATTR_VERTEX_LOCATION = 0;
    public static final int ATTR_TEXUTRE_COORD_LOCATION = 1;
    public static final int ATTR_NORMAL_LOCATION = 2;

    public MeshRendererProgram(String vertexShaderName, String fragmentShaderName, ExtendedGLSLProgramCompiler compiler) {
        super(vertexShaderName, fragmentShaderName, compiler);
    }

    public abstract void useComponent(Component component);
    public abstract void useCamera(Camera camera);
    public abstract void useMatrixStack(MatrixStack stack);
    public abstract void useEnvironment(Environment environment);

}
