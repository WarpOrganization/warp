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

    private List<btPersistentManifold> activeCollisons;

    public CollisionListener(List<btPersistentManifold> activeCollisons) {
        super();
        this.activeCollisons = activeCollisons;
    }

    @Override
    public void onContactStarted(btPersistentManifold manifold, boolean match0, boolean match1) {
        activeCollisons.add(manifold);
    }

    public void onContactEnded(btPersistentManifold manifold) {
        activeCollisons.remove(manifold);
    }
}
