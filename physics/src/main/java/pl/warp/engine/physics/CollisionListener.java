package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btManifoldPoint;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.TreeMap;

/**
 * Created by hubertus on 7/3/16.
 */

public class CollisionListener extends ContactListener {

    private TreeMap<Integer, Component> componentTreeMap;


    public CollisionListener(TreeMap<Integer, Component> componentTreeMap) {
        this.componentTreeMap = componentTreeMap;
    }

    private Vector3f tmpSpeed1 = new Vector3f();
    private Vector3f tmpSpeed2 = new Vector3f();



    @Override
    public boolean onContactAdded(btManifoldPoint cp, int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
        System.out.println("collision");
        PhysicalBodyProperty property1 = componentTreeMap.get(userValue0).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        PhysicalBodyProperty property2 = componentTreeMap.get(userValue1).getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

        tmpSpeed1.set(property1.getVelocity());
        tmpSpeed2.set(property2.getVelocity());

        property1.applyForce(tmpSpeed1.mul(-2));
        property2.applyForce(tmpSpeed2.mul(-2));

        property1.addTorque(tmpSpeed1);
        property2.addTorque(tmpSpeed2);
        return true;
    }
}