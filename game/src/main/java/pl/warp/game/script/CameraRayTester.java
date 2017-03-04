package pl.warp.game.script;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.physics.RayTester;
import pl.warp.game.GameContext;
import pl.warp.game.scene.GameComponent;

import java.util.Optional;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 15
 */
public class CameraRayTester {

    private GameContext context;
    private RayTester rayTester;

    public CameraRayTester(GameContext context, RayTester rayTester) {
        this.context = context;
        this.rayTester = rayTester;
    }

    private Vector4f tempVector = new Vector4f();
    private Vector3f tempVector2 = new Vector3f();
    private Vector3f tempVector3 = new Vector3f();
    private Matrix4f tempMatrix = new Matrix4f();

    public synchronized Optional<GameComponent> testCameraRay(float x, float y, float range){
        tempVector.zero();
        tempVector2.zero();
        tempVector3.zero();
        Camera camera = context.getGraphics().getMainViewCamera();
        Vector4f rayStart = tempVector.set(x, y, 0.0f, 1.0f);
        invertProjection(camera, rayStart);
        invertRotation(camera, rayStart);
        Vector3f directionVector = tempVector3.set(rayStart.x, rayStart.y, rayStart.z);
        Vector3f rayStart3D = camera.getPosition(tempVector2);
        directionVector.mul(range);
        Vector3f rayEnd = directionVector.add(rayStart3D);
        GameComponent result = (GameComponent) rayTester.rayTest(rayStart3D, rayEnd);
        System.out.println(result);
        if(result == null) return Optional.empty();
        else return Optional.of(result);
    }

    private void invertProjection(Camera camera, Vector4f rayStart) {
        tempMatrix.zero();
        Matrix4f invertedProjection = camera.getProjectionMatrix().getMatrix().invert(tempMatrix);
        rayStart.mul(invertedProjection);
    }

    private void invertRotation(Camera camera, Vector4f rayStart) {
        Quaternionf invertedRotation = camera.getNonrealtiveRotation();
        rayStart.rotate(invertedRotation);
    }

}
