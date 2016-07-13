package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.core.scene.listenable.ChildRemovedEvent;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by hubertus on 7/12/16.
 */
public class DefaultCollisionStrategy implements CollisionStrategy {

    private TreeMap<Integer, Component> componentTreeMap;
    private btCollisionWorld collisionWorld;
    private Set<btPersistentManifold> activeCollisons;

    private Listener<Component, ChildAddedEvent> sceneEnteredListener;
    private Listener<Component, ChildRemovedEvent> sceneLeftEventListener;

    private static final float ELASTICY = 0.1f;
    private int counter = Integer.MIN_VALUE;


    public DefaultCollisionStrategy(Component parent) {
        componentTreeMap = new TreeMap<>();
        activeCollisons = new HashSet<>();
        sceneEnteredListener = SimpleListener.createListener(parent, ChildAddedEvent.CHILD_ADDED_EVENT_NAME, this::handleSceneEntered);
        sceneLeftEventListener = SimpleListener.createListener(parent, ChildRemovedEvent.CHILD_REMOVED_EVENT_NAME, this::handleSceneLeft);
    }

    public void setCollisionWorld(btCollisionWorld collisionWorld) {
        this.collisionWorld = collisionWorld;
    }

    @Override
    public Set<btPersistentManifold> getCollisionsSet() {
        return activeCollisons;
    }

    private Vector3f relativeVelocity = new Vector3f();
    private Vector3 contactPos = new Vector3();
    private Vector3f direction = new Vector3f();
    private Vector3f dot = new Vector3f();
    private Vector3f distance1 = new Vector3f();
    private Vector3f distance2 = new Vector3f();
    private Vector3f upperPart = new Vector3f();
    private Vector3f directionCopy = new Vector3f();
    private Vector3f torqueChange = new Vector3f();

    private synchronized void handleCollision(btPersistentManifold manifold) {

        Component component1 = componentTreeMap.get(manifold.getBody0().getUserValue());
        Component component2 = componentTreeMap.get(manifold.getBody1().getUserValue());

        ColliderProperty collider1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        ColliderProperty collider2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);

        if (isCollidable(collider1) || isCollidable(collider2)) {
            TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);


            manifold.getContactPoint(0).getPositionWorldOnA(contactPos);

            //distance vector for body 1
            distance1.set(transformProperty1.getTranslation());
            distance1.sub(contactPos.x, contactPos.y, contactPos.z);
            //calculate pre-collision angular velocity of body 1
            Vector3f angularVelocity1 = new Vector3f();
            Vector3f angularVelocity2 = new Vector3f();
            angularVelocity1.set(physicalProperty1.getTorque());
            angularVelocity1.mul(distance1.length());

            //direction from contact point co body 1 center
            direction.set(transformProperty1.getTranslation());
            direction.sub(contactPos.x, contactPos.y, contactPos.z);
            direction.normalize();

            //some magic
            dot.set(distance1);
            dot.cross(direction);
            dot.cross(distance1);
            float down = dot.dot(direction) / physicalProperty1.getInteria();

            //distance vector for body 2
            distance2.set(transformProperty2.getTranslation());
            distance2.sub(contactPos.x, contactPos.y, contactPos.z);

            //pre-collision angular velocity of body 2
            angularVelocity2.set(physicalProperty2.getTorque());
            angularVelocity2.mul(distance2.length());

            //more magic
            dot.set(distance2);
            dot.cross(direction);
            dot.cross(distance2);
            down += dot.dot(direction) / physicalProperty2.getInteria();

            //relative velocity of body 1 and 2
            angularVelocity1.add(physicalProperty1.getVelocity());
            angularVelocity2.add(physicalProperty2.getVelocity());
            relativeVelocity.set(angularVelocity1);
            relativeVelocity.sub(angularVelocity2);

            //even more magic
            upperPart.set(relativeVelocity);
            upperPart.mul((1 + ELASTICY) * -1);
            float up = upperPart.dot(direction);
            down += (1 / physicalProperty1.getMass()) + (1 / physicalProperty2.getMass());
            float j = up / down;

            //torque change for body 1
            directionCopy.set(distance2).normalize();
            torqueChange.set(distance1);
            directionCopy.mul(j);
            torqueChange.cross(directionCopy);
            torqueChange.negate();

            if (isCollidable(collider1)) {
                physicalProperty1.addTorque(torqueChange);
                physicalProperty1.applyForce(direction.mul(j));
            }


            //torque change for body 2
            directionCopy.set(distance1).normalize();
            torqueChange.set(distance2);
            torqueChange.cross(directionCopy);

            if (isCollidable(collider2)) {
                physicalProperty2.addTorque(torqueChange);
                physicalProperty2.applyForce(direction.negate());
            }
        }

        component1.triggerEvent(new CollisionEvent(component2, relativeVelocity.length()));
        component2.triggerEvent(new CollisionEvent(component1, relativeVelocity.length()));
    }

    private boolean isCollidable(ColliderProperty property) {
        return property.getCollider().getDefaultCollisionHandling();
    }

    public synchronized void handleSceneEntered(ChildAddedEvent event) {
        if (event.getAddedChild().hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME)) {
            ColliderProperty tmp = event.getAddedChild().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            tmp.getCollider().addToWorld(collisionWorld, counter);
            componentTreeMap.put(counter, event.getAddedChild());
            counter++;
        }
    }


    public synchronized void handleSceneLeft(ChildRemovedEvent event) {
        if (event.getRemovedChild().hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME)) {
            ColliderProperty tmp = event.getRemovedChild().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            tmp.getCollider().removeFromWorld(collisionWorld);
            componentTreeMap.remove(tmp.getCollider().getTreeMapKey());
        }
    }

    @Override
    public void dispose() {
        collisionWorld.dispose();
    }

    @Override
    public synchronized void chceckCollisions() {
        collisionWorld.performDiscreteCollisionDetection();
        activeCollisons.forEach(this::handleCollision);

    }

    public void addActivecCollision(btPersistentManifold manifold) {
        activeCollisons.add(manifold);
    }

    public void removeActiveCollision(btPersistentManifold manifold) {
        activeCollisons.remove(manifold);
    }

}
