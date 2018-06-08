package net.warpgame.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import net.warpgame.engine.common.physics.PhysicsInterface;
import net.warpgame.engine.core.property.Property;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class FullPhysicsProperty extends Property implements PhysicsInterface {
    public static final String NAME = "physicsProperty";

    private btRigidBody rigidBody;

    public FullPhysicsProperty(btRigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    public btRigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(btRigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    private Vector3 helperVector = new Vector3();

    @Override
    public void applyCentralForce(Vector3f force) {
        rigidBody.applyCentralImpulse(helperVector.set(force.x, force.y, force.z));
    }

    private Vector3 helper2 = new Vector3();

    @Override
    public void applyTorque(Vector3f torque) {
        rigidBody.applyTorqueImpulse(helper2.set(torque.x, torque.y, torque.z));
    }
}