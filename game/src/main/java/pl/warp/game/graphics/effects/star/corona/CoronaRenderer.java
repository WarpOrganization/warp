package pl.warp.game.graphics.effects.star.corona;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.CustomRenderer;
import pl.warp.engine.graphics.Environment;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.mesh.PointVAO;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 01
 */
public class CoronaRenderer extends CustomRenderer {

    private Camera camera;
    private CoronaProgram program;
    private Environment environment;
    private PointVAO pointVAO;

    public CoronaRenderer(Graphics graphics, CoronaProgram program) {
        super(graphics);
        this.program = program;
        this.environment = graphics.getEnvironment();
    }

    @Override
    public void init() {
        this.pointVAO = new PointVAO(new Vector3f(0));
    }

    @Override
    public void initRendering(int delta) {

    }

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(CoronaProperty.CORONA_PROPERTY_NAME)) {
            CoronaProperty property = component.getProperty(CoronaProperty.CORONA_PROPERTY_NAME);
            float size = extractScale(stack.topMatrix());
            program.use();
            program.useCamera(camera);
            program.useEnvironment(environment);
            program.useMatrixStack(stack);
            program.useComponent(component);
            program.useSize(size * property.getSize());
            GL11.glDepthMask(false);
            pointVAO.draw();
            GL11.glDepthMask(true);
        } else throw new IllegalArgumentException("Can't render a corona without a mesh.");
    }


    private Vector3f temp = new Vector3f();
    private float extractScale(Matrix4f matrix){
        temp.set(matrix.m00, matrix.m01, matrix.m02);
        return temp.length();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setMainViewCamera(Camera camera) {
        this.camera = camera;
    }
}
