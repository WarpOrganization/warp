package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.GravityAffectedBodyProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Hubertus
 *         Created 2016-07-19
 */

public class CollisionHandler {

    private PhysicsWorld world;
    private CollisionStrategy collisionStrategy;

    private Component component1;
    private Component component2;
    private Vector3 contactPos = new Vector3();
    private Vector3 normal = new Vector3();

    public CollisionHandler(PhysicsWorld world, CollisionStrategy collisionStrategy) {
        this.world = world;
        this.collisionStrategy = collisionStrategy;
        result = new ClosestRayResultCallback(new Vector3(), new Vector3());
        tmpTranslation = new Vector3f();
        contactPos = new Vector3();
        rayTestNormal = new Vector3();
    }

    public void updateCollisions() {
        synchronized (world) {
            world.getCollisionWorld().performDiscreteCollisionDetection();
            world.getActiveCollisions().forEach(manifold -> {//TODO do something with contact resolution priority and penetration recalculation
                for(int i =0;i<manifold.getNumContacts();i++){
                manifold.getContactPoint(i).getPositionWorldOnA(contactPos);
                manifold.getContactPoint(i).getNormalWorldOnB(normal);
                assingValues(manifold);
                processCollision(component1, component2, manifold, i);
                }
            });
        }
    }

    private void processCollision(Component component1, Component component2, btPersistentManifold manifold, int i) {
        if (isGravityAffected(component1) && !isGravityAffected(component2)) {
            processGravityBodiesIntersection(manifold);
        } else if (isGravityAffected(component2) && !isGravityAffected(component1)) {
            processGravityBodiesIntersection(manifold);
        } else {
            collisionStrategy.calculateCollisionResponse(component1, component2, contactPos, normal);
            collisionStrategy.preventIntersection(component1, component2, contactPos, normal, -manifold.getContactPoint(i).getDistance());
        }
    }
    private boolean isGravityAffected(Component component) {
        return component.hasEnabledProperty(GravityAffectedBodyProperty.GRAVITY_AFFECTED_BODY_PROPERTY_NAME);
    }

    private void processGravityBodiesIntersection(btPersistentManifold manifold) {
        GravityAffectedBodyProperty bodyProperty = component1.getProperty(GravityAffectedBodyProperty.GRAVITY_AFFECTED_BODY_PROPERTY_NAME);
        bodyProperty.stand();
    }

    private float findLongestDistance(btPersistentManifold manifold) {
        float maxDistance = 0;
        float currentDistance;
        for (int i = 0; i < manifold.getNumContacts(); i++) {
            currentDistance = manifold.getContactPoint(i).getDistance();
            if (maxDistance > currentDistance) {
                maxDistance = currentDistance;
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

    private ClosestRayResultCallback result;
    private Vector3f tmpTranslation;
    private Vector3 rayTestNormal;

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
                result.getHitNormalWorld(rayTestNormal);
                collisionStrategy.calculateCollisionResponse(component, collider.getOwner(), contactPos, rayTestNormal);
            }
        }
    }

}
