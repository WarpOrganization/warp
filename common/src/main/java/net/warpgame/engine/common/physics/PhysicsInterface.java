package net.warpgame.engine.common.physics;

import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 08.01.2018
 */
public interface PhysicsInterface{
    void applyCentralForce(Vector3f force);
    void applyTorque(Vector3f torque);
}
