package pl.warp.engine.graphics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.framebuffer.MultisampleFramebuffer;
import pl.warp.engine.graphics.light.LightEnvironment;
import pl.warp.engine.graphics.light.SceneLightObserver;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.pipeline.Source;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.MeshProperty;
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
    private ComponentRenderer componentRenderer;
    private ComponentRendererProgram program;
    private LightEnvironment light;
    private SceneLightObserver observer;

    public SceneRenderer(Scene scene, Camera camera, RenderingSettings settings) {
        this.scene = scene;
        this.camera = camera;
        this.settings = settings;
        this.light = new LightEnvironment();
    }

    public Scene getScene() {
        return scene;
    }

    private MatrixStack matrixStack = new MatrixStack();

    @Override
    public void update(long delta) {
        renderingFramebuffer.bindDraw();
        renderingFramebuffer.clean();
        program.use();
        program.useCamera(camera);
        program.useLightEnvironment(light);
        render(scene.getRoot());
    }

    private void render(Component component) {
        matrixStack.push();
        if (component.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransformations(component);
        program.useMatrixStack(matrixStack);
        componentRenderer.render(component);
        component.forEachChildren(this::render);
        matrixStack.pop();
    }

    private void applyTransformations(Component component) { //translate, then rotate, then scale
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
    public void init() {
        setupFramebuffer();
        this.observer = new SceneLightObserver(scene, light);
        this.program = new DefaultComponentProgram();
        this.componentRenderer = new ComponentRenderer(program);
    }

    @Override
    public void destroy() {
        observer.destroy();
        renderingFramebuffer.delete();
        outputTexture.delete();
        program.delete();
        destroyComponent(scene.getRoot());
    }

    private void destroyComponent(Component component) {
        destroyProperties(component);
        component.forEachChildren(this::destroyComponent);
    }

    private void destroyProperties(Component component) {
        if (component.hasProperty(MeshProperty.MESH_PROPERTY_NAME))
            component.<MeshProperty>getProperty(MeshProperty.MESH_PROPERTY_NAME).getMesh().unload();
        if (component.hasProperty(MaterialProperty.MATERIAL_PROPERTY_NAME)) {
            Material material = component.<MaterialProperty>getProperty(MaterialProperty.MATERIAL_PROPERTY_NAME).getMaterial();
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
