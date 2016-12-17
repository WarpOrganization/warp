package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import pl.warp.engine.core.scene.Component;

/**
 * Created by hubertus on 7/12/16.
 */
public interface CollisionStrategy {

    //void handleCollision(btPersistentManifold manifold);

    void calculateCollisionResponse(Component component1, Component component2, Vector3 contactPos, Vector3 collisionNormal);

    void preventIntersection(Component component1, Component component2, Vector3 contactPos, Vector3 collisionNormal, float penetrationDepth);

    void init(PhysicsWorld world);
}
