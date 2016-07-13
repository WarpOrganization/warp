package pl.warp.engine.physics.collider;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by hubertus on 7/13/16.
 */
public class RayTestCollider implements Collider{
    @Override
    public void addToWorld(btCollisionWorld world, int treeMapKey) {

    }

    @Override
    public void removeFromWorld(btCollisionWorld world) {

    }

    @Override
    public void setTransform(Vector3f translation, Quaternionf rotation) {

    }

    @Override
    public void addTransform(Vector3f translation, Quaternion rotation) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getTreeMapKey() {
        return 0;
    }

    @Override
    public btCollisionShape getShape() {
        return null;
    }

    @Override
    public btCollisionObject getCollisionObject() {
        return null;
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void setDefaultCollisionHandling(boolean value) {

    }

    @Override
    public boolean getDefaultCollisionHandling() {
        return false;
    }
}
