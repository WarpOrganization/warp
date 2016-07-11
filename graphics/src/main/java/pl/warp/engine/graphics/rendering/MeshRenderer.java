package pl.warp.engine.graphics.rendering;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
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
public class MeshRenderer implements Renderer {

    private ComponentRendererProgram program;
    private Camera camera;
    private Environment environment;

    public MeshRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void init() {
        this.program = new DefaultComponentProgram();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    @Override
    public void initRendering(int delta) {
        setupProgram();
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME)) {
            program.use();
            program.useMatrixStack(stack);
            renderMesh(component);
        }
    }

    private void renderMesh(Component component) {
        if (component.hasEnabledProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME))
            useMaterial(component.getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME));
        enableDepthTest();
        Mesh mesh = component.<GraphicsMeshProperty>getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).getMesh();
        mesh.bind();
        mesh.render();
        mesh.finalizeRendering();
    }

    private void enableDepthTest() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }


    private void setupProgram() {
        program.use();
        program.useCamera(camera);
        program.useEnviroment(environment);
    }

    @Override
    public void destroy() {
        program.delete();
    }

    private void useMaterial(GraphicsMaterialProperty property) {
        program.useMaterial(property.getMaterial());
    }
}
