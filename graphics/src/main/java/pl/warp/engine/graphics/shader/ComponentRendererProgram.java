package pl.warp.engine.graphics.shader;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.LightEnvironment;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public abstract class ComponentRendererProgram extends Program{


    public static final int SPOT_LIGHT_POSITION = 0;
    public static final int SPOT_LIGHT_COLOR = 1;
    public static final int SPOT_LIGHT_AMBIENT_COLOR = 2;
    public static final int SPOT_LIGHT_ATTENUATION = 3;
    public static final int SPOT_LIGHT_GRADIENT = 4;
    public static final int SPOT_LIGHT_SPECULAR_FACTOR = 5;

    public static final int DIRECTIONAL_LIGHT_POSITION = 0;
    public static final int DIRECTIONAL_LIGHT_DIRECTION = 1;
    public static final int DIRECTIONAL_LIGHT_DIRECTION_GRADIENT = 2;
    public static final int DIRECTIONAL_LIGHT_COLOR = 3;
    public static final int DIRECTIONAL_LIGHT_AMBIENT_COLOR = 4;
    public static final int DIRECTIONAL_LIGHT_ATTENUATION = 5;
    public static final int DIRECTIONAL_LIGHT_GRADIENT = 6;
    public static final int DIRECTIONAL_LIGHT_SPECULAR_FACTOR = 7;

    public static final int[] ATTRIBUTES = {0, 1, 2}; //vertex, textureCoord, normal
    public static final int ATTR_VERTEX_LOCATION = 0;
    public static final int ATTR_TEXUTRE_COORD_LOCATION = 1;
    public static final int ATTR_NORMAL_LOCATION = 2;

    public ComponentRendererProgram(InputStream vertexShader, InputStream fragmentShader, String[] outNames) {
        super(vertexShader, fragmentShader, outNames);
    }

    public abstract void useMaterial(Material material);
    public abstract void useCamera(Camera camera);
    public abstract void useMatrixStack(MatrixStack stack);
    public abstract void useLightEnvironment(LightEnvironment environment);
}
