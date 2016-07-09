package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.TreeMap;

/**
 * Created by hubertus on 7/3/16.
 */

public class CollisionListener extends ContactListener {

    private TreeMap<Integer, Component> componentTreeMap;


    public CollisionListener(TreeMap<Integer, Component> componentTreeMap) {
        super();
        this.componentTreeMap = componentTreeMap;
    }

    private Vector3f force = new Vector3f();
    private Vector3 contactPos = new Vector3();
    private Vector3f relativeSpeed = new Vector3f();

    @Override
    public void onContactStarted(btPersistentManifold manifold, boolean match0, boolean match1) {
        PhysicalBodyProperty property1 = componentTreeMap.get(manifold.getBody0().getUserValue()).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
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
        property2.applyForce(force);

    }

}
