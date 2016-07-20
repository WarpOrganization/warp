package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by Hubertus on 2016-07-19.
 */

public class CollisionHandler {

    private PhysicsWorld world;
    private CollisionStrategy collisionStrategy;

    private int delta;
    private Component component1;
    private Component component2;

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
        for (btPersistentManifold manifold : world.getActiveCollisions()) {
            assingValues(manifold);
            findContactPos(manifold);
            manifold.getContactPoint(0).getPositionWorldOnA(contactPos);
            collisionStrategy.calculateCollisionResponse(component1,component2,contactPos);
        }
    }

    ClosestRayResultCallback result;
    Vector3f tmpTranslation;
    Vector3 contactPos;



    private void findContactPos(btPersistentManifold manifold) {
        //TODO: polulate
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
