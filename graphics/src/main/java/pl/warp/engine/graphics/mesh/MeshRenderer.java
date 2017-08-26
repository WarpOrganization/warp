package pl.warp.engine.graphics.mesh;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.Renderer;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;
import pl.warp.engine.graphics.program.MeshRendererProgram;
import pl.warp.engine.graphics.program.rendering.component.defaultprog.DefaultMeshProgram;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class MeshRenderer implements Renderer {

    private static final Logger logger = Logger.getLogger(LensFlareRenderer.class);

    private MeshRendererProgram defaultProgram;
    private Camera camera;
    private Environment environment;

    public MeshRenderer(Camera camera, Environment environment) {
        this.camera = camera;
        this.environment = environment;
    }

    @Override
    public void init() {
        logger.info("Initializing mesh renderer...");
        this.defaultProgram = new DefaultMeshProgram();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        logger.info("Mesh renderer initialized.");
    }

    @Override
    public void initRendering() {
        setupProgram();
    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(RenderableMeshProperty.MESH_PROPERTY_NAME)) {
            MeshRendererProgram program = getProgram(component);
            program.use();
            if (program != defaultProgram) { //TODO optimize
                program.useCamera(camera);
                program.useEnvironment(environment);
            }
            program.useMatrixStack(stack);
            program.useComponent(component);
            renderMesh(component);
        }
    }

    private MeshRendererProgram getProgram(Component component) {
        if (component.hasEnabledProperty(CustomProgramProperty.CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME)) {
            CustomProgramProperty property =
                    component.getProperty(CustomProgramProperty.CUSTOM_RENDERER_PROGRAM_PROPERTY_NAME);
            return property.getProgram();
        } else return this.defaultProgram;
    }

    private void renderMesh(Component component) {
        setupGL(); //TODO optimize
        Mesh mesh = component.<RenderableMeshProperty>getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).getMesh();
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

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
