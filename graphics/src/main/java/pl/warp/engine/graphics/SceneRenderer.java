package pl.warp.engine.graphics;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.framebuffer.MultisampleDepthTextureFramebuffer;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class SceneRenderer implements Source<MultisampleTexture2D> {

    private static final Logger logger = Logger.getLogger(SceneRenderer.class);

    private Scene scene;
    private RenderingConfig settings;
    private MultisampleDepthTextureFramebuffer renderingFramebuffer;
    private MultisampleTexture2D outputTexture;
    private MultisampleTexture2D depthTexture;
    private final ComponentRenderer renderer;

    public SceneRenderer(Scene scene, RenderingConfig settings, ComponentRenderer renderer) {
        this.scene = scene;
        this.settings = settings;
        this.renderer = renderer;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void init() {
        logger.info("Initializing scene renderer...");
        logger.info("Initializing component renderer...");

            renderer.init();
        logger.info("Setting up scene renderer framebuffers.");
        setupFramebuffer();
        logger.info("Scene renderer initialized.");
    }

    @Override
    public void update(int delta) {
        initRendering(delta);
        render(scene);
        finalizeRendering();
    }


    private void finalizeRendering() {

    }

    private void initRendering(int delta) {
        renderingFramebuffer.bindDraw();
        renderingFramebuffer.clean();
        renderer.initRendering(delta);
    }

    public void render(Component component) {
        renderer.enterChildren();
        renderer.prepareComponent(component);
        renderer.renderComponent(component);
        component.forEachChildren(this::render);
        renderer.leaveChildren();
    }




    @Override
    public void destroy() {
        renderer.destroy();
        logger.info("Component renderers destroyed.");
        renderingFramebuffer.delete();
        outputTexture.delete();
        destroyComponent(scene);
        logger.info("Scene renderer destroyed.");
    }

    private void destroyComponent(Component component) {
        destroyProperties(component);
        component.forEachChildren(this::destroyComponent);
    }

    private void destroyProperties(Component component) {
        if (component.hasProperty(RenderableMeshProperty.MESH_PROPERTY_NAME))
            component.<RenderableMeshProperty>getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).getMesh().unload();
        if (component.hasProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)) {
            Material material = component.<GraphicsMaterialProperty>getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME).getMaterial();
            material.getDiffuseTexture().delete();
            if (material.hasBrightnessTexture()) material.getBrightnessTexture().delete();
        }
    }

    private void setupFramebuffer() {
        this.outputTexture = new MultisampleTexture2D(settings.getDisplay().getWidth(), settings.getDisplay().getHeight(), GL30.GL_RGBA32F, GL11.GL_RGBA, settings.getRenderingSamples());
        this.depthTexture = new MultisampleTexture2D(settings.getDisplay().getWidth(), settings.getDisplay().getHeight(),
                GL30.GL_DEPTH_COMPONENT32F, GL11.GL_DEPTH_COMPONENT, settings.getRenderingSamples());
        this.renderingFramebuffer = new MultisampleDepthTextureFramebuffer(outputTexture, depthTexture);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.renderingFramebuffer.resize(newWidth, newHeight);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public MultisampleTexture2D getOutput() {
        return outputTexture;
    }

    public MultisampleTexture2D getDepthTexture() {
        return depthTexture;
    }
}
