package pl.warp.engine.physics.property.logic;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.physics.property.BasicColliderProperty;

/**
 * Created by hubertus on 7/7/16.
 */
public class BasicColliderLogic implements ColliderLogic {

    private BasicColliderProperty root;
    private Matrix4 transform;
    private int treeMapKey;

    public BasicColliderLogic(BasicColliderProperty root) {
        this.root = root;
        transform = new Matrix4(new Vector3(1,1,1),new Quaternion(), new Vector3(1,1,1));
        root.getCollisionObject().setWorldTransform(transform);
    }

    @Override
    public void addToWorld(btCollisionWorld world, int treeMapKey) {
        this.treeMapKey = treeMapKey;
        root.getCollisionObject().setUserValue(treeMapKey);
        world.addCollisionObject(root.getCollisionObject());
    }

    @Override
    public void removeFromWorld(btCollisionWorld world) {
        world.removeCollisionObject(root.getCollisionObject());
    }

    @Override
    public void setTransform(Vector3f translation, Quaternionf rotation) {
        transform.set(translation.x,translation.y,translation.z,rotation.x,rotation.y,rotation.z,rotation.w);
        transform.translate(root.getOffset().x, root.getOffset().y, root.getOffset().z);
        root.getCollisionObject().setWorldTransform(transform);
    }


    @Override
    public void addTransform(Vector3f translation, Quaternion rotation) {
        transform.translate(translation.x, translation.y, translation.z);
        transform.translate(root.getOffset().x, root.getOffset().y, root.getOffset().z);
        transform.rotate(rotation);
        root.getCollisionObject().setWorldTransform(transform);
    }


    @Override
    public void dispose() {
        root.getShape().dispose();
        root.getCollisionObject().dispose();
    }

    @Override
    public int getTreeMapKey() {
        return treeMapKey;
    }
}
