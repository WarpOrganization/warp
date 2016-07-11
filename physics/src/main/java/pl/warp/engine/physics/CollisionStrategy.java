package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import pl.warp.engine.core.scene.Component;

import java.util.TreeMap;

/**
 * Created by hubertus on 7/12/16.
 */
public interface CollisionStrategy {
    void handleCollision(btPersistentManifold manifold);
    TreeMap<Integer,Component> getComponentMap();
}
