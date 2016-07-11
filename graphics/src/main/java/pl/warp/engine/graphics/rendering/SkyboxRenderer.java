package pl.warp.engine.graphics.rendering;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.cubemap.CubemapProgram;
import pl.warp.engine.graphics.skybox.Skybox;
import pl.warp.engine.graphics.skybox.GraphicsSkyboxProperty;
import pl.warp.engine.graphics.texture.Cubemap;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 19
 */
public class SkyboxRenderer implements Renderer {

    private CubemapProgram cubemapProgram;
    private Skybox skybox;
    private Camera camera;

    public SkyboxRenderer(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void init() {
        this.cubemapProgram = new CubemapProgram();
        this.skybox = new Skybox();
    }

    @Override
    public void initRendering(int delta) {
        cubemapProgram.use();
        cubemapProgram.useCamera(camera);
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(GraphicsSkyboxProperty.CUBEMAP_PROPERTY_NAME)) {
            GraphicsSkyboxProperty property = component.getProperty(GraphicsSkyboxProperty.CUBEMAP_PROPERTY_NAME);
            renderCubemap(property.getCubemap());
        }
    }


    private void renderCubemap(Cubemap cubemap) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        cubemapProgram.use();
        cubemapProgram.useCubemap(cubemap);
        skybox.render();
    }

    @Override
    public void destroy() {
        skybox.delete();
        cubemapProgram.delete();
    }
}
