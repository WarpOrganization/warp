package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
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
    public void setTransform(Matrix4 transform) {
        this.transform = transform;
    }

    @Override
    public void addTransform(Vector3f translation, Vector3f rotation) {
        transform.translate(translation.x, translation.y, translation.z);
        transform.rotate(1,0,0,rotation.x).rotate(0,1,0,rotation.y).rotate(0,0,1,rotation.z);//Will it work?
        root.getCollisionObject().setWorldTransform(transform);
    }


    @Override
    public void dispose() {
        root.getShape().dispose();
        root.getCollisionObject().dispose();
    }
}
