package pl.warp.game.graphics.effects.star.corona;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
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

    private Matrix4f tempMatrix = new Matrix4f();

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(CoronaProperty.CORONA_PROPERTY_NAME)) {
            CoronaProperty property = component.getProperty(CoronaProperty.CORONA_PROPERTY_NAME);
            float size = extractScale(stack.topMatrix());

            Quaternionf rotation = getRotation(stack);

            program.use();
            program.useCamera(camera);
            program.useEnvironment(environment);
            program.useModelMatrix(stack.topMatrix());
            program.useRotationMatrix(rotation.get(tempMatrix).invert());
            program.useComponent(component);
            program.useSize(size * property.getSize());
            GL11.glDepthMask(false);
            pointVAO.draw();
            GL11.glDepthMask(true);
        } else throw new IllegalArgumentException("Can't render a corona without a mesh.");
    }

    private Vector3f temp = new Vector3f();
    private Vector4f temp2 = new Vector4f();
    private Vector3f temp3 = new Vector3f();
    private Quaternionf tempQuaternion = new Quaternionf();

    protected Quaternionf getRotation(MatrixStack stack) {
        Vector3f cameraPos = camera.getPosition(temp);
        Vector4f planetPos4 = temp2.mul(stack.topMatrix());
        Vector3f coronaPos = temp3.set(planetPos4.x, planetPos4.y, planetPos4.z).div(planetPos4.w);
        Vector3f direction = coronaPos.sub(cameraPos).negate();
        tempMatrix.identity();
        Matrix4f coronaRotation = tempMatrix.mul(stack.topRotationMatrix());
        Vector4f normal4 = temp2.set(0, 0, 1, 1);
        Vector4f rotatedNormal4 = normal4.mul(coronaRotation);
        Vector3f rotatedNormal = temp3.set(rotatedNormal4.x, rotatedNormal4.y, rotatedNormal4.z).div(rotatedNormal4.w);
        return tempQuaternion.rotateTo(rotatedNormal, direction);
    }


    private Vector3f sizeTemp = new Vector3f();

    private float extractScale(Matrix4f matrix) {
        sizeTemp.set(matrix.m00, matrix.m01, matrix.m02);
        return sizeTemp.length();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setMainViewCamera(Camera camera) {
        this.camera = camera;
    }
}
