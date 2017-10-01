package pl.warp.engine.physics.raytester;


import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.PhysicsWorld;

import java.util.Map;

/**
 * @author Hubertus
 *         Created 24.09.16
 */
@Service
public class RayTester {

    private RayTestSolver rayTestSolver;


    public RayTester(PhysicsTask physicsTask){
        rayTestSolver = physicsTask.getRayTestSolver();
    }












    private ClosestRayResultCallback result;
    private ClosestRayResultCallback result2; //used only by physics thread

    private PhysicsWorld world;

    private Map<String, RayTestTask> taskMap;

//    public RayTester(PhysicsTask physicsTask){
//        world = physicsTask.getMainWorld();
//        taskMap = new HashMap<>();
//        result = new ClosestRayResultCallback(new Vector3(), new Vector3());
//        result2 = new ClosestRayResultCallback(new Vector3(), new Vector3());
//
//    }



    public Component rayTest(Vector3 startPos, Vector3 endPos) {
        clearResult(result);
        result.setRayFromWorld(startPos);
        result.setRayToWorld(endPos);
        synchronized (world) {
            world.getCollisionWorld().rayTest(startPos, endPos, result);
        }
        if (result.hasHit()) {
            Component component;
            synchronized (world) {
                component = world.getComponent(result.getCollisionObject().getUserValue());
            }
            return component;
        } else {
            return null;
        }
    }

    public void addRayTestTask(String key, Vector3f startPos, Vector3f endPos) {
        synchronized (taskMap) {
            taskMap.put(key, new RayTestTask(startPos, endPos));
        }
    }

    public void removeRayTestTask(String key) {
        synchronized (taskMap) {
            taskMap.remove(key);
        }
    }

    public Component getComponentHit(String key) {
        return taskMap.get(key).getHit();
    }

    public RayTestTask getTask(String key){
        return taskMap.get(key);
    }

    private Vector3 startPos = new Vector3();
    private Vector3 endPos = new Vector3();

//    public synchronized Component rayTest(Vector3f startPos, Vector3f endPos) {
//        this.startPos.set(startPos.x, startPos.y, startPos.z);
//        this.endPos.set(endPos.x, endPos.y, endPos.z);
//        return rayTest(this.startPos, this.endPos);
//    }

    private void clearResult(ClosestRayResultCallback result) {
        result.setCollisionObject(null);
        result.setClosestHitFraction(1f);
    }
}
