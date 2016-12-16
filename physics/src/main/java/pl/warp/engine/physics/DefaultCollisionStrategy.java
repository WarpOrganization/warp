package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/12/16.
 */
public class DefaultCollisionStrategy implements CollisionStrategy {


    private static final float ELASTICY = 1f;

    private PhysicsWorld world;

    public void init(PhysicsWorld world) {
        this.world = world;


    }

/*    private Vector3f relativeVelocity = new Vector3f();
    private Vector3f dot = new Vector3f();
    private Vector3f distance1 = new Vector3f();
    private Vector3f distance2 = new Vector3f();
    private Vector3f upperPart = new Vector3f();
    private Vector3f directionCopy = new Vector3f();
    private Vector3f torqueChange = new Vector3f();
    private Vector3f emptyVector = new Vector3f();*/

    private Vector3f normal = new Vector3f();

    /**
     * vector from mass centre to the point of collision
     */
    private Vector3f distance1 = new Vector3f();
    private Vector3f distance2 = new Vector3f();
    private Vector3f relativeVelocity = new Vector3f();
    private Vector3f angularVelocity1 = new Vector3f();
    private Vector3f angularVelocity2 = new Vector3f();
    private Vector3f distCrossNormal1 = new Vector3f();
    private Vector3f distCrossNormal2 = new Vector3f();
    private Vector3f inertiaProduct1 = new Vector3f();
    private Vector3f inertiaProduct2 = new Vector3f();
    private Vector3f impulse = new Vector3f();

    public void calculateCollisionResponse(Component component1, Component component2, Vector3 contactPos, Vector3 collisionNormal) {
        //TODO refactor
        //TODO what the fuck
        ColliderProperty collider1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        ColliderProperty collider2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        if (isCollidable(collider1) || isCollidable(collider2)) {

            TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

            float velocity;
            float denominator;
            float j;

            //initialize variables
            normal.set(collisionNormal.x, collisionNormal.y, collisionNormal.z).normalize();

            distance1.set(transformProperty1.getTranslation());
            distance1.sub(contactPos.x, contactPos.y, contactPos.z);

            distance2.set(transformProperty2.getTranslation());
            distance2.sub(contactPos.x, contactPos.y, contactPos.z);

            angularVelocity1.set(physicalProperty1.getAngularVelocity());

            angularVelocity2.set(physicalProperty2.getAngularVelocity());

            relativeVelocity.set(physicalProperty1.getVelocity());
            relativeVelocity.sub(physicalProperty2.getVelocity());

            distance1.cross(normal, distCrossNormal1);
            distance2.cross(normal, distCrossNormal2);

            velocity = relativeVelocity.dot(normal) + distCrossNormal1.dot(angularVelocity1) - distCrossNormal2.dot(angularVelocity2);

            multiply(physicalProperty1.getRotatedInertia(), distCrossNormal1, inertiaProduct1);
            multiply(physicalProperty2.getRotatedInertia(), distCrossNormal2, inertiaProduct2);

            denominator = 1 / physicalProperty1.getMass() + 1 / physicalProperty2.getMass() + distCrossNormal1.dot(inertiaProduct1) + distCrossNormal2.dot(inertiaProduct2);

            j = velocity / denominator * ((1 + ELASTICY));
            //System.out.println(j);

            normal.mul(j, impulse);

            //impulse.negate();
            if (isCollidable(collider2)) {
                physicalProperty2.applyForce(impulse);
                physicalProperty2.addTorque(impulse, distance2);
            }

            impulse.negate();

            if (isCollidable(collider1)) {
                physicalProperty1.applyForce(impulse);
                physicalProperty1.addTorque(impulse, distance1);
            }
        /*ColliderProperty collider1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        ColliderProperty collider2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);

        if (isCollidable(collider1) || isCollidable(collider2)) {
            TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

            //physicalProperty1.setAngularVelocity(emptyVector);
            //physicalProperty2.setAngularVelocity(emptyVector);


            normal.set(collisionNormal.x, collisionNormal.y, collisionNormal.z);


            ////////////////////////////////////////////////////////////////////////////////////



            normal.set(collisionNormal.x, collisionNormal.y, collisionNormal.z);

            //distance vector for body 1
            distance1.set(transformProperty1.getTranslation());
            distance1.sub(contactPos.x, contactPos.y, contactPos.z);
            //calculate pre-collision angular velocity of body 1
            Vector3f angularVelocity1 = new Vector3f();
            Vector3f angularVelocity2 = new Vector3f();
            angularVelocity1.set(physicalProperty1.getAngularVelocity());
            angularVelocity1.mul(distance1.length());

            //some magic
            dot.set(distance1);
            dot.cross(normal);
            dot.cross(distance1);
            float down = dot.dot(normal) / physicalProperty1.getInertia();

            //distance vector for body 2
            distance2.set(transformProperty2.getTranslation());
            distance2.sub(contactPos.x, contactPos.y, contactPos.z);

            //pre-collision angular velocity of body 2
            angularVelocity2.set(physicalProperty2.getAngularVelocity());
            angularVelocity2.mul(distance2.length());

            //more magic
            dot.set(distance2);
            dot.cross(normal);
            dot.cross(distance2);
            down += dot.dot(normal) / physicalProperty2.getInertia();

            //relative velocity of body 1 and 2
            angularVelocity1.add(physicalProperty1.getVelocity());
            angularVelocity2.add(physicalProperty2.getVelocity());
            relativeVelocity.set(angularVelocity1);
            relativeVelocity.sub(angularVelocity2);

            //even more magic
            upperPart.set(relativeVelocity);
            upperPart.mul((1 + ELASTICY) * -1);
            float up = upperPart.dot(normal);
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
                physicalProperty1.applyForce(normal.mul(j));
            }

            //torque change for body 2
            directionCopy.set(distance1).normalize();
            torqueChange.set(distance2);
            torqueChange.cross(directionCopy);

            if (isCollidable(collider2)) {
                physicalProperty2.addTorque(torqueChange);
                physicalProperty2.applyForce(normal.negate());
            }
            System.out.println(j);
        }

        component1.triggerEvent(new CollisionEvent(component2, relativeVelocity.length()));
        component2.triggerEvent(new CollisionEvent(component1, relativeVelocity.length()));*/

        }
    }


    //private Vector3 contactPos = new Vector3();

    //can't remember if it was meant to be used somewhere
/*
    @Override
    public void handleCollision(btPersistentManifold manifold) {
        Component component1;
        Component component2;
        component1 = world.getComponent(manifold.getBody0().getUserValue());
        component2 = world.getComponent(manifold.getBody1().getUserValue());
        manifold.getContactPoint(0).getPositionWorldOnA(contactPos);
        calculateCollisionResponse(component1, component2, contactPos);
    }
*/

    private boolean isCollidable(ColliderProperty property) {
        return property.getCollider().getDefaultCollisionHandling();
    }


    private void multiply(Matrix3f matrix, Vector3f vector, Vector3f out) {
        out.x = matrix.m00 * vector.x + matrix.m10 * vector.x + matrix.m10 * vector.x;
        out.y = matrix.m01 * vector.y + matrix.m11 * vector.y + matrix.m11 * vector.y;
        out.z = matrix.m02 * vector.z + matrix.m12 * vector.z + matrix.m12 * vector.z;
    }


}
