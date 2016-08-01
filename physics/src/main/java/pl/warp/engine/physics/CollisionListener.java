package pl.warp.engine.physics;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;

import java.util.List;
import java.util.Set;

/**
 * Created by hubertus on 7/3/16.
 */

public class CollisionListener extends ContactListener {

    private PhysicsWorld world;

    public CollisionListener(PhysicsWorld world) {
        super();
        this.world = world;
    }

    @Override
    public void onContactStarted(btPersistentManifold manifold, boolean match0, boolean match1) {
        synchronized (world) {
            world.getActiveCollisions().add(manifold);
        }
    }

    public void onContactEnded(btPersistentManifold manifold) {
        synchronized (world) {
            world.getActiveCollisions().remove(manifold);
        }
    }
}
