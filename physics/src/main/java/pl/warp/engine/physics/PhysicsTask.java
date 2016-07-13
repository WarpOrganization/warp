package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import org.joml.Vector3f;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.physics.collider.BasicCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/4/16.
 */

public class PhysicsTask extends EngineTask {

    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase dbvtBroadphase;
    private btDefaultCollisionConfiguration defaultCollisionConfiguration;
    private CollisionListener collisionListener;

    private CollisionStrategy collisionStrategy;
    private Component parent;


    public PhysicsTask(CollisionStrategy collisionStrategy, Component parent) {

        this.collisionStrategy = collisionStrategy;
        this.parent = parent;
    }

    @Override
    protected void onInit() {
        new SharedLibraryLoader().load("gdx");
        Bullet.init();
        defaultCollisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(defaultCollisionConfiguration);
        dbvtBroadphase = new btDbvtBroadphase();
        collisionStrategy.setCollisionWorld(new btCollisionWorld(dispatcher, dbvtBroadphase, defaultCollisionConfiguration));
        collisionListener = new CollisionListener(collisionStrategy.getCollisionsSet());
        collisionListener.enableOnAdded();
        parent.forEachChildren(component -> {
            if (component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)) {
                new ColliderProperty(component, new BasicCollider(new btBoxShape(new Vector3(2.1465f, 0.6255f, 2.833f)), new Vector3f(-0.067f, 0, 0), CollisionType.COLLISION_NORMAL, CollisionType.COLLISION_NORMAL));
                collisionStrategy.handleSceneEntered(new ChildAddedEvent(component));
            }
        });
    }

    @Override
    protected void onClose() {
        dispatcher.dispose();
        dbvtBroadphase.dispose();
        defaultCollisionConfiguration.dispose();
        collisionListener.dispose();
        collisionStrategy.dispose();
    }


    @Override
    public void update(int delta) {
        collisionStrategy.chceckCollisions();
    }
}
