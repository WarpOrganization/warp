package pl.warp.engine.physics;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.collider.Collider;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by Hubertus on 2016-07-19.
 */

public class CollisionHandler {

    private static final float ACCURACY = 0.1f;

    private PhysicsWorld world;
    private CollisionStrategy collisionStrategy;

    private int delta;
    private Component component1;
    private Component component2;
    private Vector3 contactPos = new Vector3();

    public CollisionHandler(PhysicsWorld world, CollisionStrategy collisionStrategy) {

        this.world = world;
        this.collisionStrategy = collisionStrategy;
        result = new ClosestRayResultCallback(new Vector3(), new Vector3());
        tmpTranslation = new Vector3f();
        contactPos = new Vector3();
    }


    public void updateCollisions(int delta) {
        this.delta = delta;
        world.getCollisionWorld().performDiscreteCollisionDetection();
        world.getActiveCollisions().forEach(manifold -> {
            manifold.getContactPoint(0).getPositionWorldOnA(contactPos);
            assingValues(manifold);
            collisionStrategy.calculateCollisionResponse(component1, component2, contactPos);
        });
    }

    ClosestRayResultCallback result;
    Vector3f tmpTranslation;
    Vector3f stepTranslation1 = new Vector3f();
    Vector3f stepTranslation2 = new Vector3f();
    Quaternionf stepRotation1 = new Quaternionf();
    Quaternionf stepRotation2 = new Quaternionf();
    Vector3f tmp = new Vector3f();
    CollisionObjectWrapper wrapper1 = new CollisionObjectWrapper(new btCollisionObject());
    CollisionObjectWrapper wrapper2 = new CollisionObjectWrapper(new btCollisionObject());
    btCollisionConfiguration collisionConfiguration = new btDefaultCollisionConfiguration();
    btCollisionDispatcher dispatcher = new btCollisionDispatcher(collisionConfiguration);

    private void findContactPos(btPersistentManifold manifold) {
        Component component1;
        Component component2;
        synchronized (world) {
            component1 = world.getComponent(manifold.getBody0().getUserValue());
            component2 = world.getComponent(manifold.getBody1().getUserValue());
        }

        TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        PhysicalBodyProperty physicalBodyProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        PhysicalBodyProperty physicalBodyProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        ColliderProperty colliderProperty1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        ColliderProperty colliderProperty2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);

        float distance = findLongestDistance(manifold);

    }

    private float findLongestDistance(btPersistentManifold manifold){
        float maxDistance=0;
        float currentDistance;
        for(int i = 0; i< manifold.getNumContacts();i++){
            currentDistance = manifold.getContactPoint(i).getDistance();
            if(maxDistance>currentDistance){
                maxDistance = currentDistance;
                //contactPos.set(manifold.getContactPoint(0).getW)
            }
        }
        return maxDistance;
    }


    private void assingValues(btPersistentManifold manifold) {
        synchronized (world) {
            component1 = world.getComponent(manifold.getBody0().getUserValue());
            component2 = world.getComponent(manifold.getBody1().getUserValue());
        }
    }

    private void redoSimulation() {

    }

    public void performRayTests() {
        for (int i = 0; i < world.getRayTestColliders().size(); i++) {
            PointCollider collider;
            synchronized (world) {
                collider = world.getRayTestColliders().get(i);
            }
            result.setCollisionObject(null);
            result.setClosestHitFraction(1f);
            result.setRayFromWorld(collider.getLastPos());
            result.setRayToWorld(collider.getCurrentPos());
            synchronized (world) {
                world.getCollisionWorld().rayTest(collider.getLastPos(), collider.getCurrentPos(), result);
            }
            if (result.hasHit()) {
                result.getHitPointWorld(contactPos);
                TransformProperty property = collider.getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                PhysicalBodyProperty physicalBodyProperty = collider.getOwner().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
                tmpTranslation.set(physicalBodyProperty.getVelocity());
                tmpTranslation.normalize();
                tmpTranslation.negate();
                tmpTranslation.mul(physicalBodyProperty.getRadius());
                tmpTranslation.add(contactPos.x, contactPos.y, contactPos.z);
                property.setTranslation(tmpTranslation);
                Component component;
                component = world.getComponent(result.getCollisionObject().getUserValue());
                collisionStrategy.calculateCollisionResponse(component, collider.getOwner(), contactPos);
            }
        }
    }

}
