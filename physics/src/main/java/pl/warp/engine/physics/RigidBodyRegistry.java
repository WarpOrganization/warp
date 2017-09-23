package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class RigidBodyRegistry {
    private Set<btRigidBody> toAdd = new HashSet<>();
    private Set<btRigidBody> toRemove = new HashSet<>();

    public synchronized void addRigidBody(btRigidBody rigidBody) {
        toAdd.add(rigidBody);
    }

    public synchronized void removeRigidBody(btRigidBody rigidBody) {
        toRemove.add(rigidBody);
    }

    synchronized void processBodies(btDynamicsWorld dynamicsWorld) {
        if (!toAdd.isEmpty()) {
            for (btRigidBody aToAdd : toAdd) {
                dynamicsWorld.addRigidBody(aToAdd);
            }
            toAdd.clear();
        }

        if (!toRemove.isEmpty()) {
            for (btRigidBody aToRemove : toRemove) {
                dynamicsWorld.removeRigidBody(aToRemove);
            }
            toAdd.clear();
        }
    }
}
