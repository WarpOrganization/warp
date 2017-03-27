package pl.warp.game.graphics.effects.atmosphere;

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
import pl.warp.engine.graphics.mesh.shapes.Sphere;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 12
 */
public class AtmosphereRenderer extends CustomRenderer {

    private Camera camera;
    private AtmosphereProgram program;
    private Environment environment;
    private Sphere sphere;

    public AtmosphereRenderer(Graphics graphics, AtmosphereProgram program) {
        super(graphics);
        this.program = program;
        this.environment = graphics.getEnvironment();
    }

    @Override
    public void init() {
        this.sphere = new Sphere(200, 200);
    }

    @Override
    public void initRendering() {

    }

    private Matrix4f tempMatrix = new Matrix4f();

    @Override
    public void render(Component component, MatrixStack stack) {
        if (component.hasEnabledProperty(AtmosphereProperty.ATMOSPHERE_PROPERTY_NAME)) {
            stack.push();
            Quaternionf rotation = getRotation(stack);
            program.use();
            program.useCamera(camera);
            program.useEnvironment(environment);
            program.useModelMatrix(stack.topMatrix());
            program.useRotationMatrix(rotation.get(tempMatrix));
            program.useComponent(component);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDepthMask(false);
            sphere.draw();
            GL11.glDepthMask(true);
            stack.pop();
        } else throw new IllegalArgumentException("Can't render a corona without a mesh.");
    }

    private Vector3f temp = new Vector3f();
    private Vector4f temp2 = new Vector4f();
    private Vector3f temp3 = new Vector3f();
    private Quaternionf tempQuaternion = new Quaternionf();

    protected Quaternionf getRotation(MatrixStack stack) {
        temp2.set(0, 0, 0, 1);
        this.tempQuaternion.identity();
        Vector3f cameraPos = camera.getPosition(temp);
        Vector4f planetPos4 = temp2.mul(stack.topMatrix());
        Vector3f coronaPos = temp3.set(planetPos4.x, planetPos4.y, planetPos4.z).div(planetPos4.w);
        Vector3f direction = coronaPos.sub(cameraPos).negate();
        tempMatrix.identity();
        Matrix4f coronaRotation = tempMatrix.mul(stack.topRotationMatrix());
        Vector4f normal4 = temp2.set(0, 0, 1, 1);
        Vector4f rotatedNormal4 = normal4.mul(coronaRotation);
        Vector3f rotatedNormal = temp.set(rotatedNormal4.x, rotatedNormal4.y, rotatedNormal4.z).div(rotatedNormal4.w);
        return this.tempQuaternion.rotateTo(rotatedNormal, direction);
    }

    @Override
    public void destroy() {
        this.sphere.destroy();
    }

    @Override
    public void setMainViewCamera(Camera camera) {
        this.camera = camera;
    }
}
