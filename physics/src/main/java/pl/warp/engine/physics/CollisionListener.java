package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.TreeMap;

/**
 * Created by hubertus on 7/3/16.
 */

public class CollisionListener extends ContactListener {

    private TreeMap<Integer, Component> componentTreeMap;

    public static final float ELASTICY = 1f;


    public CollisionListener(TreeMap<Integer, Component> componentTreeMap) {
        super();
        this.componentTreeMap = componentTreeMap;
    }

    private Vector3f force = new Vector3f();
    private Vector3 contactPos = new Vector3();
    private Vector3f relativeSpeed = new Vector3f();

    @Override
    public void onContactStarted(btPersistentManifold manifold, boolean match0, boolean match1) {
   /*     PhysicalBodyProperty property1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        PhysicalBodyProperty property2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        TransformProperty transformProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        TransformProperty transformProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);

        manifold.getContactPoint(0).getPositionWorldOnA(contactPos);
        relativeSpeed.set(property2.getVelocity());
        relativeSpeed.sub(property1.getVelocity());

        force.set(transformProperty1.getTranslation());
        force.sub(contactPos.x, contactPos.y, contactPos.z);
        force.normalize();
        force.mul(relativeSpeed.length());
        property1.applyForce(force);

        manifold.getContactPoint(0).getPositionWorldOnB(contactPos);
        force.set(transformProperty2.getTranslation());
        force.sub(contactPos.x, contactPos.y, contactPos.z);
        force.normalize();
        force.mul(relativeSpeed.length());
        property2.applyForce(force);*/

        TransformProperty transformProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        TransformProperty transformProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        PhysicalBodyProperty physicalProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        PhysicalBodyProperty physicalProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        ColliderProperty colliderProperty1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
        ColliderProperty colliderProperty2 = componentTreeMap.get(manifold.getBody1().getUserValue()).getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);


        manifold.getContactPoint(0).getPositionWorldOnA(contactPos);

        Vector3f direction = new Vector3f();
        direction.set(transformProperty1.getScale());
        direction.sub(contactPos.x, contactPos.y, contactPos.z);
        direction.normalize();

        relativeSpeed.set(physicalProperty1.getVelocity());
        relativeSpeed.sub(physicalProperty2.getVelocity());

        Vector3f up = new Vector3f();
        up.set(relativeSpeed);
        up.mul((1 + ELASTICY) * -1);
        float upScalar = up.dot(direction);
        float down = (1 / physicalProperty1.getMass()) + (1 / physicalProperty2.getMass());

        float interia1 = colliderProperty1.getCollider().getRadius() * colliderProperty1.getCollider().getRadius() * physicalProperty1.getMass();
        float interia2 = colliderProperty2.getCollider().getRadius() * colliderProperty2.getCollider().getRadius() * physicalProperty2.getMass();
        Vector3f distance = new Vector3f();
        distance.set(transformProperty1.getTranslation());
        distance.sub(contactPos.x, contactPos.y, contactPos.z);
        Vector3f dot = new Vector3f();
        dot.set(distance);
        dot.cross(direction);
        dot.cross(distance);
        down += dot.dot(direction)/interia1;

        distance.set(transformProperty2.getTranslation());
        distance.sub(contactPos.x, contactPos.y, contactPos.z);
        dot.set(distance);
        dot.cross(direction);
        dot.cross(distance);
        down += dot.dot(direction)/interia2;

        float j = upScalar/down;

        physicalProperty1.applyForce(direction.mul(j));
        physicalProperty2.applyForce(direction.negate());
    }

}
