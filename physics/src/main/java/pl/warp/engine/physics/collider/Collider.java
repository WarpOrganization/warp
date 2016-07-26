package pl.warp.engine.physics.collider;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.physics.PhysicsWorld;

/**
 * Created by hubertus on 7/9/16.
 */
public interface Collider {
    void addToWorld(PhysicsWorld world);

    void removeFromWorld();

    void setTransform(Vector3f translation, Quaternionf rotation);

    void addTransform(Vector3f translation, Quaternionf rotation);

    void dispose();

    void setDefaultCollisionHandling(boolean value);

    btCollisionObject getCollisionObject();

    boolean getDefaultCollisionHandling();
}
