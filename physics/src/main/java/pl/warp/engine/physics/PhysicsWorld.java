package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;

/**
 * @author Hubertus
 * Created 22.09.2017
 */
public class PhysicsWorld {
    private btDynamicsWorld dynamicsWorld;

    public PhysicsWorld(btDynamicsWorld dynamicsWorld) {
        this.dynamicsWorld = dynamicsWorld;
    }

    public btDynamicsWorld getDynamicsWorld() {
        return dynamicsWorld;
    }

    public void setDynamicsWorld(btDynamicsWorld dynamicsWorld) {
        this.dynamicsWorld = dynamicsWorld;
    }
}
