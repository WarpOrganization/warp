package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;

/**
 * Created by hubertus on 7/7/16.
 */
public interface ColliderPropertyLogic {
    void addToWorld(btCollisionWorld world);
    void removeFromWorld(btCollisionWorld world);
    void setTransform(Matrix4 transform);
    void dispose();
}
