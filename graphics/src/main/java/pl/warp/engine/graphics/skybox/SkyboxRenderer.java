package pl.warp.engine.graphics.skybox;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.shader.program.cubemap.CubemapProgram;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 19
 */
public class SkyboxRenderer implements Renderer {

    private static final Logger logger = Logger.getLogger(SkyboxRenderer.class);

    private CubemapProgram cubemapProgram;
    private Skybox skybox;
    private Camera camera;

    public SkyboxRenderer(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void init() {
        logger.info("Initializing skybox renderer...");
        this.cubemapProgram = new CubemapProgram();
        this.skybox = new Skybox();
        logger.info("Skybox renderer initialized.");
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
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            cubemapProgram.use();
            cubemapProgram.useBrightness(property.getBrightness());
            cubemapProgram.useCubemap(property.getCubemap());
            skybox.render();
        }
    }



    @Override
    public void destroy() {
        skybox.delete();
        cubemapProgram.delete();
        logger.info("Skybox renderer destroyed.");
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
