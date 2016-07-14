package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.core.scene.listenable.ChildRemovedEvent;

import java.util.Set;

/**
 * Created by hubertus on 7/12/16.
 */
public interface CollisionStrategy {

    void setCollisionWorld(btCollisionWorld collisionWorld);

    Set<btPersistentManifold> getCollisionsSet();

    void checkCollisions();

    void handleSceneEntered(ChildAddedEvent event);

    void handleSceneLeft(ChildRemovedEvent event);

    void dispose();
}
