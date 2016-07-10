package pl.warp.engine.physics.collider;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by hubertus on 7/9/16.
 */
public interface Collider {
    void addToWorld(btCollisionWorld world, int treeMapKey);

    void removeFromWorld(btCollisionWorld world);

    void setTransform(Vector3f translation, Quaternionf rotation);

    void addTransform(Vector3f translation, Quaternion rotation);

    void dispose();

    int getTreeMapKey();

    btCollisionShape getShape();

    btCollisionObject getCollisionObject();

    float getRadius();

    void activate();

    void deactivate();
}
