package pl.warp.engine.physics.collider;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.ComponentDeathEvent;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.physics.PhysicsWorld;

/**
 * Created by hubertus on 7/9/16.
 */
public class BasicCollider implements Collider {
    private final btCollisionShape shape;
    private btCollisionObject collisionObject;
    private Component owner;
    private Vector3f offset;
    private Matrix4 transform;
    private int treeMapKey;
    private int callbackFilter;
    private int callbackFlag;
    private boolean defaultCollisionHandling;
    private PhysicsWorld world;

    private Listener<Component, ComponentDeathEvent> deathListener;

    public BasicCollider(btCollisionShape shape, Component owner, Vector3f offset, int callbackFilter, int callbackFlag) {

        this.shape = shape;
        this.owner = owner;
        this.offset = offset;
        this.callbackFilter = callbackFilter;
        this.callbackFlag = callbackFlag;
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(shape);
        transform = new Matrix4(new Vector3(1, 1, 1), new Quaternion(), new Vector3(1, 1, 1));
        collisionObject.setWorldTransform(transform);
        collisionObject.setContactCallbackFilter(callbackFilter);
        collisionObject.setContactCallbackFlag(callbackFlag);
        defaultCollisionHandling = true;

        deathListener = SimpleListener.createListener(owner, ComponentDeathEvent.COMPONENT_DEATH_EVENT_NAME, this::suicide);
    }

    @Override
    public void addToWorld(PhysicsWorld world) {
        collisionObject.setUserValue(world.getCounter());
        treeMapKey = world.getCounter();
        world.addToCollisionWorld(collisionObject);
        world.addToComponentMap(owner);
        this.world = world;
    }

    @Override
    public void removeFromWorld() {
        synchronized (world) {
            world.removeFromCollisionWorld(collisionObject);
            world.removeFromComponentMap(treeMapKey);
        }
    }

    @Override
    public void setTransform(Vector3f translation, Quaternionf rotation) {
        transform.set(translation.x, translation.y, translation.z, rotation.x, rotation.y, rotation.z, rotation.w);
        transform.translate(offset.x, offset.y, offset.z);
        collisionObject.setWorldTransform(transform);
    }

    @Override
    public void addTransform(Vector3f translation, Quaternion rotation) {
        transform.translate(translation.x, translation.y, translation.z);
        transform.rotate(rotation);
        collisionObject.setWorldTransform(transform);
    }

    @Override
    public void dispose() {
        shape.dispose();
        collisionObject.dispose();
    }

    @Override
    public void setDefaultCollisionHandling(boolean value) {
        defaultCollisionHandling = value;
    }

    @Override
    public boolean getDefaultCollisionHandling() {
        return defaultCollisionHandling;
    }

    private void suicide(ComponentDeathEvent event) {
        removeFromWorld();
        collisionObject.dispose();
        shape.dispose();
    }
}
