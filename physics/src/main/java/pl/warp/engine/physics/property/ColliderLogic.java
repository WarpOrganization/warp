package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import org.joml.Vector3f;

/**
 * Created by hubertus on 7/7/16.
 */
public interface ColliderLogic {
    void addToWorld(btCollisionWorld world);
    void removeFromWorld(btCollisionWorld world);
    void setTransform(Matrix4 transform);
    void addTransform(Vector3f translation, Vector3f rotation);
    void dispose();
}
