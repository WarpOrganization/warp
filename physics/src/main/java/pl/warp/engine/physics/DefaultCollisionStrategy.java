package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Hubertus
 *         Created 7/12/16
 */
//TODO cleanup
public class DefaultCollisionStrategy implements CollisionStrategy {


    private static final float ELASTICY = 0.8f;

    private PhysicsWorld world;

    public void init(PhysicsWorld world) {
        this.world = world;
    }

    private Vector3f normal = new Vector3f();

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
    private Vector3f position1 = new Vector3f();
    private Vector3f postition2 = new Vector3f();

    public void calculateCollisionResponse(Component component1, Component component2, Vector3 contactPos, Vector3 collisionNormal) {
        if (component1.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME) && component2.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME) //quickfix TODO repair
                && (isCollidable(component1) && isCollidable(component2))) {
            ColliderProperty collider1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            ColliderProperty collider2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

            float velocity;
            float denominator;
            float j;

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

            normal.mul(j, impulse);

            if (isCollidable(component1)) {
                physicalProperty2.applyForce(impulse);
                physicalProperty2.addTorque(impulse, distance2);
            }

            impulse.negate();

            if (isCollidable(component2)) {
                physicalProperty1.applyForce(impulse);
                physicalProperty1.addTorque(impulse, distance1);
            }

            component1.triggerEvent(new CollisionEvent(component2, relativeVelocity.length()));
            component2.triggerEvent(new CollisionEvent(component1, relativeVelocity.length()));

        }
    }

    private Vector3f linearMovement = new Vector3f();
    private Vector3f rotationPerMove = new Vector3f();

    public void preventIntersection(Component component1, Component component2, Vector3 contactPos, Vector3 collisionNormal, float penetrationDepth) {
        if (component1.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME) && component2.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME) //quickfix TODO repair
                && (isCollidable(component1) && isCollidable(component2))) {
            ColliderProperty collider1 = component1.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            ColliderProperty collider2 = component2.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
            TransformProperty transformProperty1 = component1.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            TransformProperty transformProperty2 = component2.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty1 = component1.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            PhysicalBodyProperty physicalProperty2 = component2.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

            normal.set(collisionNormal.x, collisionNormal.y, collisionNormal.z).normalize();

            position1.set(transformProperty1.getTranslation());
            //position1.add(physicalProperty1.getNextTickTranslation());
            distance1.set(position1);
            distance1.sub(contactPos.x, contactPos.y, contactPos.z);

            postition2.set(transformProperty2.getTranslation());
            //postition2.add(physicalProperty2.getNextTickTranslation());
            distance2.set(postition2);
            distance2.sub(contactPos.x, contactPos.y, contactPos.z);

            distance1.cross(normal, distCrossNormal1);
            distance2.cross(normal, distCrossNormal2);

            multiply(physicalProperty1.getRotatedInertia(), distCrossNormal1, inertiaProduct1);
            multiply(physicalProperty2.getRotatedInertia(), distCrossNormal2, inertiaProduct2);
            float linearInertia1 = 1 / physicalProperty1.getMass();
            float linearInertia2 = 1 / physicalProperty2.getMass();
            float angularInertia1 = distCrossNormal1.dot(inertiaProduct1);
            float angularInertia2 = distCrossNormal2.dot(inertiaProduct2);
            float totalInertia = linearInertia1 + linearInertia2 + angularInertia1 + angularInertia2;
            float reversedTotalIntertia = 1 / totalInertia;

            float linear1 = penetrationDepth * linearInertia1 * reversedTotalIntertia;
            float linear2 = -penetrationDepth * linearInertia2 * reversedTotalIntertia;
            float angular1 = penetrationDepth * angularInertia1 * reversedTotalIntertia;
            float angular2 = -penetrationDepth * angularInertia2 * reversedTotalIntertia;

            normal.mul(linear1, linearMovement);
            physicalProperty1.getNextTickTranslation().add(linearMovement);

            normal.mul(linear2, linearMovement);
            physicalProperty2.getNextTickRotation().add(linearMovement);


            //TODO fix angular
            inertiaProduct1.mul(1 / angularInertia1, rotationPerMove);
            rotationPerMove.mul(angular1);
            //physicalProperty1.getNextTickRotation().add(rotationPerMove);

            inertiaProduct2.mul(1 / angularInertia2, rotationPerMove);
            rotationPerMove.mul(angular2);
            //physicalProperty2.getNextTickRotation().add(rotationPerMove);
        }
    }

    Vector3f downVector = new Vector3f();
    Vector3f downComponent = new Vector3f();

    public void calculateFloorCollision(Component floor, Component body, float depth) {
        GravityProperty gravityProperty = body.getProperty(GravityProperty.GRAVITY_PROPERTY_NAME);
        TransformProperty transform = body.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        PhysicalBodyProperty bodyProperty = body.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        downVector.set(gravityProperty.getDownVector());
        downVector.normalize();

        downComponent.set(downVector);
        downComponent.mul(bodyProperty.getVelocity().dot(downVector));

        downVector.negate();
        downVector.mul(depth);
        transform.move(downVector);
        gravityProperty.setStanding(true);
        bodyProperty.getVelocity().sub(downComponent);
        //TODO update relative velocity
        floor.triggerEvent(new CollisionEvent(body, 0));
        body.triggerEvent(new CollisionEvent(floor, 0));
    }


    private boolean isCollidable(Component c) {
        return c.hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME) &&
                c.<ColliderProperty>getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).getCollider().getDefaultCollisionHandling();
    }


    private void multiply(Matrix3f matrix, Vector3f vector, Vector3f out) {
        out.x = matrix.m00 * vector.x + matrix.m10 * vector.x + matrix.m10 * vector.x;
        out.y = matrix.m01 * vector.y + matrix.m11 * vector.y + matrix.m11 * vector.y;
        out.z = matrix.m02 * vector.z + matrix.m12 * vector.z + matrix.m12 * vector.z;
    }


}
