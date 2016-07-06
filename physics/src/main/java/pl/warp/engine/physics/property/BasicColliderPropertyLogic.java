package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;

/**
 * Created by hubertus on 7/7/16.
 */
public class BasicColliderPropertyLogic implements ColliderPropertyLogic{

    private BasicColliderProperty root;

    public BasicColliderPropertyLogic(BasicColliderProperty root){

        this.root = root;
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
        root.getCollisionObject().setWorldTransform(transform);
    }

    @Override
    public void dispose() {
        root.getShape().dispose();
        root.getCollisionObject().dispose();
    }
}
