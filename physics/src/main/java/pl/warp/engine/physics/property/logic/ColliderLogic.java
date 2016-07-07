package pl.warp.engine.physics.property.logic;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by hubertus on 7/7/16.
 */
public interface ColliderLogic {
    void addToWorld(btCollisionWorld world);

    void removeFromWorld(btCollisionWorld world);

    void setTransform(Vector3f translation, Quaternionf rotation);

    void addTransform(Vector3f translation, Quaternion rotation);

    void dispose();
}
