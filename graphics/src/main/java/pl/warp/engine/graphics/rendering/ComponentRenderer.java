package pl.warp.engine.graphics.rendering;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.light.Environment;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.shader.component.defaultprog.DefaultComponentProgram;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class ComponentRenderer implements Renderer {

    private ComponentRendererProgram program;
    private MatrixStack matrixStack = new MatrixStack();
    private Camera camera;
    private Environment environment;

    public ComponentRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void init() {
        this.program = new DefaultComponentProgram();
    }

    @Override
    public void render(Scene scene, int delta) {
        setupRendering();
        useProgram();
        renderComponent(scene);
    }

    private void setupRendering() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    private void useProgram() {
        program.use();
        program.useCamera(camera);
        program.useLightEnvironment(environment);
    }

    @Override
    public void destroy() {
        program.delete();
    }

    public void renderComponent(Component component) {
        matrixStack.push();
        if (component.hasEnabledProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransformations(component);
        if (component.hasEnabledProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME)) {
            program.useMatrixStack(matrixStack);
            renderMesh(component);
        }
        component.forEachChildren(this::renderComponent);
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


    private void renderMesh(Component component) {
        if (component.hasEnabledProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME))
            useMaterial(component.getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME));
        Mesh mesh = component.<GraphicsMeshProperty>getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).getMesh();
        mesh.bind();
        mesh.render();
        mesh.finalizeRendering();
    }

    private void useMaterial(GraphicsMaterialProperty property) {
        program.useMaterial(property.getMaterial());
    }
}
