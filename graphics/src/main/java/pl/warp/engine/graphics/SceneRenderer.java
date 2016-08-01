package pl.warp.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.framebuffer.MultisampleFramebuffer;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class SceneRenderer implements Source<MultisampleTexture2D> {

    private Scene scene;
    private RenderingConfig settings;
    private MultisampleFramebuffer renderingFramebuffer;
    private MultisampleTexture2D outputTexture;
    private Renderer[] renderers;
    private MatrixStack matrixStack = new MatrixStack();


    public SceneRenderer(Scene scene, RenderingConfig settings, Renderer[] renderers) {
        this.scene = scene;
        this.settings = settings;
        this.renderers = renderers;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void init() {
        for (Renderer renderer : renderers)
            renderer.init();
        setupFramebuffer();
    }

    @Override
    public void update(int delta) {
        initRendering(delta);
        render(scene);
    }

    private void initRendering(int delta) {
        renderingFramebuffer.bindDraw();
        renderingFramebuffer.clean();
        for (Renderer renderer : renderers)
            renderer.initRendering(delta);
    }

    private void render(Component component) {
        matrixStack.push();
        if(component.hasEnabledProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransformations(component);
        renderComponent(component);
        component.forEachChildren(this::render);
        matrixStack.pop();
    }

    private void renderComponent(Component component) {
        for (Renderer renderer : renderers)
            renderer.render(component, matrixStack);
    }

    private void applyTransformations(Component component) {
        TransformProperty property = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        applyTranslation(property);
        applyScale(property);
        applyRotation(property);
    }

    private void applyScale(TransformProperty scale) {
        matrixStack.scale(scale.getScale());
    }

    private void applyRotation(TransformProperty rotation) {
        matrixStack.rotate(rotation.getRotation());
    }

    private void applyTranslation(TransformProperty translation) {
        matrixStack.translate(translation.getTranslation());
    }


    @Override
    public void destroy() {
        for (Renderer renderer : renderers)
            renderer.destroy();
        renderingFramebuffer.delete();
        outputTexture.delete();
        destroyComponent(scene);
    }

    private void destroyComponent(Component component) {
        destroyProperties(component);
        component.forEachChildren(this::destroyComponent);
    }

    private void destroyProperties(Component component) {
        if (component.hasProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME))
            component.<GraphicsMeshProperty>getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).getMesh().unload();
        if (component.hasProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)) {
            Material material = component.<GraphicsMaterialProperty>getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME).getMaterial();
            material.getMainTexture().delete();
            if (material.hasBrightnessTexture()) material.getBrightnessTexture().delete();
        }
    }

    private void setupFramebuffer() {
        this.outputTexture = new MultisampleTexture2D(settings.getDisplayWidth(), settings.getDisplayHeight(), GL30.GL_RGBA32F, GL11.GL_RGBA, settings.getRenderingSamples());
        this.renderingFramebuffer = new MultisampleFramebuffer(outputTexture);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.renderingFramebuffer.resize(newWidth, newHeight);
    }

    @Override
    public MultisampleTexture2D getOutput() {
        return outputTexture;
    }
}
