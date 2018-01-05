package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import net.warpgame.engine.core.property.Property;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class PhysicsProperty extends Property {
    public static final String NAME = "physicsProperty";

    private btRigidBody rigidBody;

    public PhysicsProperty(btRigidBody rigidBody){
        super(NAME);
        this.rigidBody = rigidBody;
    }

    public btRigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(btRigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }
}
