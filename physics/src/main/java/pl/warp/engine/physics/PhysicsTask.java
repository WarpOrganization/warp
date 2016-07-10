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
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.collider.BasicCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.HashSet;
import java.util.Set;
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
    private Set<btPersistentManifold> activeCollisons;

    public static final float ELASTICY = 1f;

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
        activeCollisons = new HashSet<>();
        collisionListener = new CollisionListener(componentTreeMap, activeCollisons);
        collisionListener.enableOnAdded();
        sceneEnteredListener = SimpleListener.createListener(parent, ChildAddedEvent.CHILD_ADDED_EVENT_NAME, this::handleSceneEntered);
        sceneLeftEventListener = SimpleListener.createListener(parent, ChildRemovedEvent.CHILD_REMOVED_EVENT_NAME, this::handleSceneLeft);
        parent.forEachChildren(component -> {
            if (component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)) {
                ColliderProperty property = new ColliderProperty(component, new BasicCollider(new btBoxShape(new Vector3(2.833f, 0.6255f, 2.1465f)), new Vector3f(-0.067f, 0, 0), 2.833f, CollisionType.COLLISION_NORMAL, CollisionType.COLLISION_NORMAL));
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

    private Vector3f relativeSpeed = new Vector3f();
    private Vector3 contactPos = new Vector3();
    private Vector3f direction = new Vector3f();
    private Vector3f dot = new Vector3f();
    private Vector3f distance = new Vector3f();
    private Vector3f upperPart = new Vector3f();

    @Override
    public void update(long delta) {
        collisionWorld.performDiscreteCollisionDetection();
        activeCollisons.forEach(manifold ->
        {
            TransformProperty transformProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            ColliderProperty colliderProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            ColliderProperty colliderProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            Vector3f v = new Vector3f();


            manifold.getContactPoint(0).getPositionWorldOnA(contactPos);

            direction.set(transformProperty1.getTranslation());
            direction.sub(contactPos.x, contactPos.y, contactPos.z);
            direction.normalize();

            relativeSpeed.set(physicalProperty1.getVelocity());
            relativeSpeed.sub(physicalProperty2.getVelocity());

            upperPart.set(relativeSpeed);
            upperPart.mul((1 + ELASTICY) * -1);
            float up = upperPart.dot(direction);
            float down = (1 / physicalProperty1.getMass()) + (1 / physicalProperty2.getMass());

            float interia1 = colliderProperty1.getCollider().getRadius() * colliderProperty1.getCollider().getRadius() * physicalProperty1.getMass();
            float interia2 = colliderProperty2.getCollider().getRadius() * colliderProperty2.getCollider().getRadius() * physicalProperty2.getMass();
            distance.set(transformProperty1.getTranslation());
            distance.sub(contactPos.x, contactPos.y, contactPos.z);
            dot.set(distance);
            dot.cross(direction);
            dot.cross(distance);
            down += dot.dot(direction) / interia1;

            distance.set(transformProperty2.getTranslation());
            distance.sub(contactPos.x, contactPos.y, contactPos.z);
            dot.set(distance);
            dot.cross(direction);
            dot.cross(distance);
            down += dot.dot(direction) / interia2;

            float j = up / down;

            physicalProperty1.applyForce(direction.mul(j));
            physicalProperty2.applyForce(direction.negate());
        });
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
