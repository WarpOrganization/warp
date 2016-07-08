package pl.warp.engine.graphics.rendering;

import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.shader.cubemap.CubemapProgram;
import pl.warp.engine.graphics.skybox.Skybox;
import pl.warp.engine.graphics.skybox.SkyboxProperty;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 19
 */
public class SkyboxRenderer {

    private CubemapProgram cubemapProgram;
    private Skybox skybox;
    private Camera camera;

    public SkyboxRenderer(Camera camera) {
        this.cubemapProgram = new CubemapProgram();
        this.skybox = new Skybox();
        this.camera = camera;
    }

    public void render(Scene scene) {
        if (scene.hasEnabledProperty(SkyboxProperty.CUBEMAP_PROPERTY_NAME)) {
            SkyboxProperty property = scene.getProperty(SkyboxProperty.CUBEMAP_PROPERTY_NAME);
            renderCubemap(property.getCubemap());
        }
    }

    private void renderCubemap(Cubemap cubemap) {
        cubemapProgram.use();
        cubemapProgram.useCamera(camera);
        cubemapProgram.useCubemap(cubemap);
        skybox.render();
    }

    public void delete(){
        skybox.delete();
        cubemapProgram.delete();
    }
}
