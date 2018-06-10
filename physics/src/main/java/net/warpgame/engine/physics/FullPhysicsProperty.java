package net.warpgame.engine.physics;

import com.badlogic.gdx.math.Vector3;
import net.warpgame.engine.common.physics.PhysicsInterface;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.physics.constraints.Constraint;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class FullPhysicsProperty extends Property implements PhysicsInterface {

    private RigidBody rigidBody;
    private Set<Constraint> constraints = new HashSet<>();

    public FullPhysicsProperty(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
        rigidBody
                .getBulletRigidBody()
                .setUserValue(getOwner().getId());
        getOwner()
                .getContext()
                .getLoadedContext()
                .findOne(PhysicsService.class)
                .get()
                .addRigidBody(getOwner());
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    //TODO might require more work
//    public void setRigidBody(RigidBody rigidBody) {
//        this.rigidBody = rigidBody;
//    }

    private Vector3 helperVector = new Vector3();

    @Override
    public void applyCentralForce(Vector3f force) {
        rigidBody
                .getBulletRigidBody()
                .applyCentralImpulse(helperVector.set(force.x, force.y, force.z));
    }

    private Vector3 helper2 = new Vector3();

    @Override
    public void applyTorque(Vector3f torque) {
        rigidBody
                .getBulletRigidBody()
                .applyTorqueImpulse(helper2.set(torque.x, torque.y, torque.z));
    }

    public void setActivationState(Collision.ActivationState activationState) {
        rigidBody.getBulletRigidBody().setActivationState(activationState.val);
    }

    public void activate() {
        rigidBody.getBulletRigidBody().activate();
    }

    public void removeConstraint(Constraint constraint) {
        removeConstraint(constraint.getID());
    }

    public void removeConstraint(int constraintId) {
        getOwner()
                .getContext()
                .getLoadedContext()
                .findOne(PhysicsService.class)
                .get()
                .removeConstraint(constraintId);
    }

    void internalRemoveConstraint(Constraint constraint){
        constraints.remove(constraint);
    }

    void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }
}