package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/12/16.
 */
public class DefaultCollisionStrategy implements CollisionStrategy {


    private static final float ELASTICY = 0.1f;

    private PhysicsWorld world;

    public void init(PhysicsWorld world) {
        this.world = world;


    }

    private Vector3f relativeVelocity = new Vector3f();
    private Vector3f direction = new Vector3f();
    private Vector3f dot = new Vector3f();
    private Vector3f distance1 = new Vector3f();
    private Vector3f distance2 = new Vector3f();
    private Vector3f upperPart = new Vector3f();
    private Vector3f directionCopy = new Vector3f();
    private Vector3f torqueChange = new Vector3f();

    public void calculateCollisionResponse(Component component1, Component component2, Vector3 contactPos) {

        ColliderProperty collider1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        ColliderProperty collider2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);

        if (isCollidable(collider1) || isCollidable(collider2)) {
            TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

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
                physicalProperty1.addTorque(torqueChange.negate());
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


    private Vector3 contactPos = new Vector3();

    @Override
    public void handleCollision(btPersistentManifold manifold) {
        Component component1;
        Component component2;
        component1 = world.getComponent(manifold.getBody0().getUserValue());
        component2 = world.getComponent(manifold.getBody1().getUserValue());
        manifold.getContactPoint(0).getPositionWorldOnA(contactPos);
        calculateCollisionResponse(component1, component2, contactPos);
    }

    private boolean isCollidable(ColliderProperty property) {
        return property.getCollider().getDefaultCollisionHandling();
    }

}
