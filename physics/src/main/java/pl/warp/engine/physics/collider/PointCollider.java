package pl.warp.engine.physics.collider;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.physics.PhysicsWorld;

/**
 * @author Hubertus
 *         Created 7/13/16
 */
public class PointCollider implements Collider {

    private final Component owner;
    private Vector3 lastPos;
    private Vector3 currentPos;
    private boolean defaultCollisionHandling = true;
    private PhysicsWorld world;

    public PointCollider(Component owner, Vector3 translation) {
        this.owner = owner;
        lastPos = new Vector3(translation);
        currentPos = new Vector3(translation);

    }

    @Override
    public void addToWorld(PhysicsWorld world) {
        world.addRayTest(this);
        this.world = world;
    }

    @Override
    public void removeFromWorld() {
        world.addDestroyedRayTest(this);
    }

    @Override
    public void setTransform(Vector3f translation, Quaternionf rotation) {
        lastPos.set(currentPos);
        currentPos.set(translation.x, translation.y, translation.z);
    }

    @Override
    public void addTransform(Vector3f translation, Quaternionf rotation) {
        lastPos.set(currentPos);
        currentPos.add(translation.x, translation.y, translation.z);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setDefaultCollisionHandling(boolean value) {
        defaultCollisionHandling = value;
    }

    @Override
    public btCollisionObject getCollisionObject() {
        return null;
    }

    @Override
    public boolean getDefaultCollisionHandling() {
        return defaultCollisionHandling;
    }

    public Vector3 getCurrentPos() {
        return currentPos;
    }

    public Vector3 getLastPos() {
        return lastPos;
    }

    public Component getOwner() {
        return owner;
    }

}
