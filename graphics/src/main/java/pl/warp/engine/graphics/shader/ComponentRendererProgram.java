package pl.warp.engine.graphics.shader;

import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.material.Material;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public interface ComponentRendererProgram {
    int getAttrVertex();
    int getAttrTexCoord();
    int getAttrNormal();
    int getUnifProjectionMatrix();
    int getUnifModelMatrix();
    int getUnifRotationMatrix();
    int getUnifCameraMatrix();
    int getUnifSpotLightSources();
    int getUnifDirectionalLightSources();
    int getMaxSpotLightSourcesCount();
    int getMaxDirectionaLightSourcesCount();
    int getUnifMainTexture();
    int getUnifCameraPos();
    void useMaterial(Material material);
    void useCamera(Camera camera);
}
