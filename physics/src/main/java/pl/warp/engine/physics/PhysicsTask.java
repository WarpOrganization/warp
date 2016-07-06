package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.physics.property.ColliderProperty;

/**
 * Created by hubertus on 7/4/16.
 */

public class PhysicsTask extends EngineTask {

    private btCollisionWorld collisionWorld;
    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase dbvtBroadphase;
    private btDefaultCollisionConfiguration defaultCollisionConfiguration;
    private CollisionListener collisionListener;

    private Listener<Component, SceneEnteredEvent> sceneEnteredListener;
    private Listener<Component, SceneLeftEvent> sceneLeftEventListener;

    @Override
    protected void onInit() {
        Bullet.init();
        defaultCollisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(defaultCollisionConfiguration);
        dbvtBroadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, dbvtBroadphase, defaultCollisionConfiguration);
        collisionListener = new CollisionListener();

        //sceneEnteredListener = SimpleListener.createListener()
        //sceneLeftEventListener = SimpleListener.createListener()
    }

    @Override
    protected void onClose() {
        dispatcher.dispose();
        dbvtBroadphase.dispose();
        defaultCollisionConfiguration.dispose();
        collisionListener.dispose();
        collisionWorld.dispose();
    }

    @Override
    public void update(long delta) {
        collisionWorld.performDiscreteCollisionDetection();
    }

    private void handleSceneEntered(SceneEnteredEvent event){
        ColliderProperty tmp = event.getComponent().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        tmp.addToWorld(collisionWorld);
    }

    private void handleSceneLeft(SceneLeftEvent event){
        ColliderProperty tmp = event.getComponent().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        tmp.removeFromWorld(collisionWorld);
    }
}
