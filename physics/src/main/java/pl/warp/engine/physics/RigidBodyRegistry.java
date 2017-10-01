package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import pl.warp.engine.core.component.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class RigidBodyRegistry {
    private Set<Component> toAdd = new HashSet<>();
    private Set<Component> toRemove = new HashSet<>();
    private ColliderComponentRegistry colliderComponentRegistry;


    public RigidBodyRegistry(ColliderComponentRegistry colliderComponentRegistry) {
        this.colliderComponentRegistry = colliderComponentRegistry;
    }

    public synchronized void addRigidBody(Component component) {
        toAdd.add(component);
    }

    public synchronized void removeRigidBody(Component component) {
        toRemove.add(component);
    }

    synchronized void processBodies(btDynamicsWorld dynamicsWorld) {
        if (!toAdd.isEmpty()) {
            for (Component aToAdd : toAdd) {
                colliderComponentRegistry.addComponennt(aToAdd);
                PhysicsProperty physicsProperty = aToAdd.getProperty(PhysicsProperty.PHYSICS_PROPERTY_NAME);
                dynamicsWorld.addRigidBody(physicsProperty.getRigidBody());
            }
            toAdd.clear();
        }

        if (!toRemove.isEmpty()) {
            for (Component aToRemove : toRemove) {
                PhysicsProperty physicsProperty = aToRemove.getProperty(PhysicsProperty.PHYSICS_PROPERTY_NAME);
                dynamicsWorld.removeRigidBody(physicsProperty.getRigidBody());
                colliderComponentRegistry.removeCompoent(physicsProperty.getRigidBody().getUserValue());
            }
            toAdd.clear();
        }
    }
}
