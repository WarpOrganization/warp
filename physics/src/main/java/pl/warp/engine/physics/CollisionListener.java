package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import pl.warp.engine.core.scene.Component;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by hubertus on 7/3/16.
 */

public class CollisionListener extends ContactListener {

    private TreeMap<Integer, Component> componentTreeMap;
    private Set<btPersistentManifold> activeCollisons;


    public CollisionListener(TreeMap<Integer, Component> componentTreeMap, Set<btPersistentManifold> activeCollisons) {
        super();
        this.componentTreeMap = componentTreeMap;
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
