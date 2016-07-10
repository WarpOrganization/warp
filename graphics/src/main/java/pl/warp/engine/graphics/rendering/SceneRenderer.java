package pl.warp.engine.graphics.rendering;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.RenderingSettings;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.framebuffer.MultisampleFramebuffer;
import pl.warp.engine.graphics.light.LightEnvironment;
import pl.warp.engine.graphics.light.SceneLightObserver;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.shader.component.defaultprog.DefaultComponentProgram;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;

import static org.lwjgl.opengl.GL30.GL_RGBA32F;

/**
 * @author Jaca777
 *         Created 2016-06-29 at 21
 */
public class SceneRenderer implements Source<MultisampleTexture2D> {

    private Scene scene;
    private Camera camera;
    private RenderingSettings settings;
    private MultisampleFramebuffer renderingFramebuffer;
    private MultisampleTexture2D outputTexture;
    private ComponentRendererProgram program;
    private LightEnvironment light;
    private SceneLightObserver observer;

    private ComponentRenderer componentRenderer;
    private SkyboxRenderer cubemapRenderer;


    public SceneRenderer(Scene scene, Camera camera, RenderingSettings settings) {
        this.scene = scene;
        this.camera = camera;
        this.settings = settings;
        this.light = new LightEnvironment();
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(int delta) {
        renderingFramebuffer.bindDraw();
        renderingFramebuffer.clean();
        cubemapRenderer.render(scene);
        program.use();
        program.useCamera(camera);
        program.useLightEnvironment(light);
        componentRenderer.render(scene);
    }


    @Override
    public void init() {
        setupFramebuffer();
        this.observer = new SceneLightObserver(scene, light);
        this.program = new DefaultComponentProgram();
        this.componentRenderer = new ComponentRenderer(program);
        this.cubemapRenderer = new SkyboxRenderer(camera);
    }

    @Override
    public void destroy() {
        observer.destroy();
        renderingFramebuffer.delete();
        outputTexture.delete();
        program.delete();
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
