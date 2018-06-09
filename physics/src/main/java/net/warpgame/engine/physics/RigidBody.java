package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class RigidBody implements Disposable {
    private btRigidBody bulletRigidBody;
    private PhysicsMotionState physicsMotionState;

    RigidBody(btRigidBody bulletRigidBody, PhysicsMotionState physicsMotionState){
        this.bulletRigidBody = bulletRigidBody;
        this.physicsMotionState = physicsMotionState;
    }

    /**
     * Changes position of rigid body collider relative to owner component's transform property.
     */
    public void setOffset(Vector3f translationOffset, Quaternionf rotationOffset){
        physicsMotionState.setOffset(translationOffset, rotationOffset);
    }

    void setBulletRigidBody(btRigidBody bulletRigidBody) {
        this.bulletRigidBody = bulletRigidBody;
    }

    /**
     * Interfering with native btRigidBody can be unsafe.
     */
    public btRigidBody getBulletRigidBody() {
        return bulletRigidBody;
    }

    @Override
    public void dispose() {
        bulletRigidBody.dispose();
    }
}
