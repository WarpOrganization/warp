package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by hubertus on 7/7/16.
 */
public class BasicColliderLogic implements ColliderLogic {

    private BasicColliderProperty root;
    private Matrix4 transform;

    public BasicColliderLogic(BasicColliderProperty root) {
        this.root = root;
        transform = new Matrix4();
        root.getCollisionObject().setWorldTransform(transform);
    }

    @Override
    public void addToWorld(btCollisionWorld world) {
        world.addCollisionObject(root.getCollisionObject());
    }

    @Override
    public void removeFromWorld(btCollisionWorld world) {
        world.removeCollisionObject(root.getCollisionObject());
    }

    @Override
    public void setTransform(Vector3f translation, Quaternionf rotation) {
        transform.setToTranslation(translation.x, translation.y, translation.z);
        transform.setToRotation(rotation.x, rotation.y, rotation.z, rotation.w);
        root.getCollisionObject().setWorldTransform(transform);
    }


    @Override
    public void addTransform(Vector3f translation, Quaternion rotation) {
        transform.translate(translation.x, translation.y, translation.z);
        transform.rotate(rotation);
        root.getCollisionObject().setWorldTransform(transform);
    }


    @Override
    public void dispose() {
        root.getShape().dispose();
        root.getCollisionObject().dispose();
    }
}
