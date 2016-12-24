package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hubertus
 *         Created 01.08.16
 */
public class RayTester {

    private ClosestRayResultCallback result;
    private ClosestRayResultCallback result2; //used only by physics thread

    private PhysicsWorld world;

    private Map<String, RaytestTask> taskMap;

    public void init(PhysicsWorld world){
        this.world = world;
        taskMap = new HashMap<>();
        result = new ClosestRayResultCallback(new Vector3(), new Vector3());
        result2 = new ClosestRayResultCallback(new Vector3(), new Vector3());
    }

    public void update() {
        taskMap.forEach((s, raytestTask) -> {
            clearResult(result2);
            result2.setRayFromWorld(raytestTask.getStartPos());
            result2.setRayToWorld(raytestTask.getEndPos());
            synchronized (world) {
                world.getCollisionWorld().rayTest(raytestTask.getStartPos(), raytestTask.getEndPos(), result2);
            }

            if (result2.hasHit()) {

                raytestTask.setHasHit(true);
                Component hit;

                synchronized (world) {
                    hit = world.getComponent(result2.getCollisionObject().getUserValue());
                }

                raytestTask.setHit(hit);

            } else {
                raytestTask.setHasHit(false);
                raytestTask.setHit(null);
            }
        });
    }

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
            taskMap.put(key, new RaytestTask(startPos, endPos));
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

    public RaytestTask getTask(String key){
        return taskMap.get(key);
    }

    private Vector3 startPos = new Vector3();
    private Vector3 endPos = new Vector3();

    public Component rayTest(Vector3f startPos, Vector3f endPos) {
        this.startPos.set(startPos.x, startPos.y, startPos.z);
        this.endPos.set(endPos.x, endPos.y, endPos.z);

        return rayTest(this.startPos, this.endPos);
    }

    private void clearResult(ClosestRayResultCallback result) {
        result.setCollisionObject(null);
        result.setClosestHitFraction(1f);
    }
}
