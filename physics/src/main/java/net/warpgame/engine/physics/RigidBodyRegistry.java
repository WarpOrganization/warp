package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.property.Property;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class RigidBodyRegistry {
    private Set<Component> toAdd = new HashSet<>();
    private Set<Component> toRemove = new HashSet<>();

    public synchronized void addRigidBody(Component component) {
        toAdd.add(component);
    }

    public synchronized void removeRigidBody(Component component) {
        toRemove.add(component);
    }

    synchronized void processBodies(btDynamicsWorld dynamicsWorld) {
        if (!toAdd.isEmpty()) {
            for (Component aToAdd : toAdd) {
                FullPhysicsProperty fullPhysicsProperty = aToAdd.getProperty(Property.getTypeId(FullPhysicsProperty.class));
                btRigidBody bulletRigidBody = fullPhysicsProperty.getRigidBody().getBulletRigidBody();
                dynamicsWorld.addRigidBody(bulletRigidBody);
                bulletRigidBody.activate();
            }
            toAdd.clear();
        }

        if (!toRemove.isEmpty()) {
            for (Component aToRemove : toRemove) {
                FullPhysicsProperty fullPhysicsProperty = aToRemove.getProperty(Property.getTypeId(FullPhysicsProperty.class));
                dynamicsWorld.removeRigidBody(fullPhysicsProperty.getRigidBody().getBulletRigidBody());
            }
            toAdd.clear();
        }
    }
}
