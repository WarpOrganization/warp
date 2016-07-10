package pl.warp.engine.graphics.rendering;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.RenderingSettings;
import pl.warp.engine.graphics.framebuffer.MultisampleFramebuffer;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

import static org.lwjgl.opengl.GL30.GL_RGBA32F;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class SceneRenderer implements Source<MultisampleTexture2D> {

    private Scene scene;
    private RenderingSettings settings;
    private MultisampleFramebuffer renderingFramebuffer;
    private MultisampleTexture2D outputTexture;
    private Renderer[] renderers;


    public SceneRenderer(Scene scene, RenderingSettings settings, Renderer[] renderers) {
        this.scene = scene;
        this.settings = settings;
        this.renderers = renderers;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(int delta) {
        renderingFramebuffer.bindDraw();
        renderingFramebuffer.clean();
        for (Renderer renderer : renderers)
            renderer.render(scene, delta);
    }


    @Override
    public void init() {
        for(Renderer renderer : renderers)
            renderer.init();
        setupFramebuffer();
    }

    @Override
    public void destroy() {
        for(Renderer renderer : renderers)
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
            if (material.hasSpecularTexture()) material.getSpecularTexture().delete();
        }
    }

    private void setupFramebuffer() {
        this.outputTexture = new MultisampleTexture2D(settings.getWidth(), settings.getHeight(), GL_RGBA32F, settings.getRenderingSamples());
        this.renderingFramebuffer = new MultisampleFramebuffer(outputTexture);
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public MultisampleTexture2D getOutput() {
        return outputTexture;
    }
}
