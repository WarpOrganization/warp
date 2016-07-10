package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import org.joml.Vector3f;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.core.scene.listenable.ChildRemovedEvent;
import pl.warp.engine.core.scene.listenable.ListenableParent;
import pl.warp.engine.physics.collider.BasicCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.TreeMap;

/**
 * Created by hubertus on 7/4/16.
 */

public class PhysicsTask extends EngineTask {

    private btCollisionWorld collisionWorld;
    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase dbvtBroadphase;
    private btDefaultCollisionConfiguration defaultCollisionConfiguration;
    private CollisionListener collisionListener;

    private Listener<Component, ChildAddedEvent> sceneEnteredListener;
    private Listener<Component, ChildRemovedEvent> sceneLeftEventListener;

    private TreeMap<Integer, Component> componentTreeMap;
    private int counter = Integer.MIN_VALUE;
    private ListenableParent parent;


    public PhysicsTask(ListenableParent parent) {

        this.parent = parent;
    }

    @Override
    protected void onInit() {
        Bullet.init();
        defaultCollisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(defaultCollisionConfiguration);
        dbvtBroadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, dbvtBroadphase, defaultCollisionConfiguration);
        componentTreeMap = new TreeMap<>();
        collisionListener = new CollisionListener(componentTreeMap);
        collisionListener.enableOnAdded();
        sceneEnteredListener = SimpleListener.createListener(parent, ChildAddedEvent.CHILD_ADDED_EVENT_NAME, this::handleSceneEntered);
        sceneLeftEventListener = SimpleListener.createListener(parent, ChildRemovedEvent.CHILD_REMOVED_EVENT_NAME, this::handleSceneLeft);
        parent.forEachChildren(component -> {
            if (component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)) {
                ColliderProperty property = new ColliderProperty(component, new BasicCollider(new btBoxShape(new Vector3(2.833f, 0.6255f, 2.1465f)), new Vector3f(-0.067f, 0, 0), 2.833f));
                property.getCollider().addToWorld(collisionWorld, counter);
                componentTreeMap.put(counter, component);
                counter++;
            }
        });
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
    public void update(int delta) {
        collisionWorld.performDiscreteCollisionDetection();
    }
    private void handleSceneEntered(ChildAddedEvent event) {
        ColliderProperty tmp = event.getAddedChild().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        tmp.getCollider().addToWorld(collisionWorld, counter);
        componentTreeMap.put(counter, event.getAddedChild());
        counter++;
    }

    private void handleSceneLeft(ChildAddedEvent event) {
        ColliderProperty tmp = event.getAddedChild().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        tmp.getCollider().removeFromWorld(collisionWorld);
        componentTreeMap.remove(tmp.getCollider().getTreeMapKey());
    }
}
