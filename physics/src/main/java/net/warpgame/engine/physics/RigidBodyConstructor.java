package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.physics.shapeconstructors.RigidBodyShapeConstructor;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class RigidBodyConstructor {
    private RigidBodyShapeConstructor rigidBodyShapeConstructor;
    private float mass;

    public RigidBodyConstructor(RigidBodyShapeConstructor rigidBodyShapeConstructor, float mass) {
        this.rigidBodyShapeConstructor = rigidBodyShapeConstructor;
        this.mass = mass;
    }

    /**
     * @param transformProperty - rigid body owner's transform property
     * @return RigidBody ready to be used in FullPhysicsProperty() constructor
     */
    public RigidBody construct(TransformProperty transformProperty) {
        if (!rigidBodyShapeConstructor.isConstructed()) rigidBodyShapeConstructor.construct();

        PhysicsMotionState motionState = new PhysicsMotionState(transformProperty);
        btRigidBody bulletRigidBody = new btRigidBody(
                mass,
                motionState,
                rigidBodyShapeConstructor.getShape(),
                rigidBodyShapeConstructor.calculateInertia(mass));

        return new RigidBody(bulletRigidBody, motionState);
    }

    public RigidBodyShapeConstructor getRigidBodyShapeConstructor() {
        return rigidBodyShapeConstructor;
    }

    public void setRigidBodyShapeConstructor(RigidBodyShapeConstructor rigidBodyShapeConstructor) {
        this.rigidBodyShapeConstructor = rigidBodyShapeConstructor;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
