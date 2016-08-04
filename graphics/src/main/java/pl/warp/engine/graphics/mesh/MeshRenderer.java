package pl.warp.engine.graphics.mesh;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.shader.program.component.defaultprog.DefaultComponentProgram;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class MeshRenderer implements Renderer {

    private static final Logger logger = Logger.getLogger(LensFlareRenderer.class);

    private ComponentRendererProgram defaultProgram;
    private Camera camera;
    private Environment environment;

    public MeshRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void init() {
        logger.info("Initializing mesh renderer...");
        this.defaultProgram = new DefaultComponentProgram();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        logger.info("Mesh renderer initialized.");
    }

    @Override
    public void initRendering(int delta) {
        setupProgram();
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME)) {
            ComponentRendererProgram program = getProgram(component);
            program.use();
            program.useCamera(camera);
            program.useEnvironment(environment);
            program.useMatrixStack(stack);
            program.useComponent(component);
            renderMesh(component);
        }
    }

    private ComponentRendererProgram getProgram(Component component) {
        if (component.hasEnabledProperty(GraphicsCustomRendererProgramProperty.CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME)) {
            GraphicsCustomRendererProgramProperty property =
                    component.getProperty(GraphicsCustomRendererProgramProperty.CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME);
            return property.getProgram();
        } else return this.defaultProgram;
    }

    private void renderMesh(Component component) {
        setupGL();
        Mesh mesh = component.<GraphicsMeshProperty>getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).getMesh();
        mesh.draw();
    }

    private void setupGL() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }


    private void setupProgram() {
        defaultProgram.use();
        defaultProgram.useCamera(camera);
        defaultProgram.useEnvironment(environment);
    }

    @Override
    public void destroy() {
        defaultProgram.delete();
    }
}
