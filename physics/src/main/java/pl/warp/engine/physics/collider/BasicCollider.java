package pl.warp.engine.physics.collider;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by hubertus on 7/9/16.
 */
public class BasicCollider implements Collider {
    private final btCollisionShape shape;
    private btCollisionObject collisionObject;
    private Vector3f offset;
    private Matrix4 transform;
    private int treeMapKey;
    private float radius;
    private int callbackFilter;
    private int callbackFlag;

    public BasicCollider(btCollisionShape shape, Vector3f offset, float radius, int callbackFilter, int callbackFlag) {

        this.shape = shape;
        this.offset = offset;
        this.radius = radius;
        this.callbackFilter = callbackFilter;
        this.callbackFlag = callbackFlag;
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(shape);
        transform = new Matrix4(new Vector3(1, 1, 1), new Quaternion(), new Vector3(1, 1, 1));
        collisionObject.setWorldTransform(transform);
        collisionObject.setContactCallbackFilter(callbackFilter);
        collisionObject.setContactCallbackFlag(callbackFlag);
    }

    @Override
    public void addToWorld(btCollisionWorld world, int treeMapKey) {
        this.treeMapKey = treeMapKey;
        collisionObject.setUserValue(treeMapKey);
        world.addCollisionObject(collisionObject);
    }

    @Override
    public void removeFromWorld(btCollisionWorld world) {
        world.removeCollisionObject(collisionObject);
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
    public int getTreeMapKey() {
        return treeMapKey;
    }

    @Override
    public btCollisionObject getCollisionObject() {
        return collisionObject;
    }

    @Override
    public btCollisionShape getShape() {
        return shape;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public void activate() {
        collisionObject.setContactCallbackFilter(callbackFilter);
        collisionObject.setContactCallbackFlag(callbackFlag);
    }

    @Override
    public void deactivate() {
        collisionObject.setContactCallbackFlag(1);
        collisionObject.setContactCallbackFilter(0);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
