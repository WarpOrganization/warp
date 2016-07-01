package pl.warp.engine.graphics.shader;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.LightEnvironment;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public interface ComponentRendererProgram {

    int SPOT_LIGHT_POSITION = 0;
    int SPOT_LIGHT_COLOR = 1;
    int SPOT_LIGHT_AMBIENT_COLOR = 2;
    int SPOT_LIGHT_ATTENUATION = 3;
    int SPOT_LIGHT_GRADIENT = 4;
    int SPOT_LIGHT_SPECULAR_FACTOR = 5;

    int DIRECTIONAL_LIGHT_POSITION = 0;
    int DIRECTIONAL_LIGHT_DIRECTION = 1;
    int DIRECTIONAL_LIGHT_DIRECTION_GRADIENT = 2;
    int DIRECTIONAL_LIGHT_COLOR = 3;
    int DIRECTIONAL_LIGHT_AMBIENT_COLOR = 4;
    int DIRECTIONAL_LIGHT_ATTENUATION = 5;
    int DIRECTIONAL_LIGHT_GRADIENT = 6;
    int DIRECTIONAL_LIGHT_SPECULAR_FACTOR = 7;

    int ATTR_VERTEX_LOCATION = 0;
    int ATTR_TEXUTRE_COORD_LOCATION = 1;
    int ATTR_NORMAL_LOCATION = 2;

    void useMaterial(Material material);
    void useCamera(Camera camera);
    void useMatrixStack(MatrixStack stack);
    void useLightEnvironment(LightEnvironment environment);
}
