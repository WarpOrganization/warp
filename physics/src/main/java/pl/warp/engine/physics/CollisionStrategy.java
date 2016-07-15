package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;

/**
 * Created by hubertus on 7/12/16.
 */
public interface CollisionStrategy {
    void handleCollision(btPersistentManifold manifold);

    void performRayTests();

    void init(PhysicsWorld world);
}
