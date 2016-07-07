package pl.warp.engine.physics.property.logic;

import org.joml.Vector3f;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/7/16.
 */
public class PhysicalBodyLogic {

    private PhysicalBodyProperty root;

    public PhysicalBodyLogic(PhysicalBodyProperty root){

        this.root = root;
    }

    public synchronized void applyForce(Vector3f force){
        root.getSpeed().add(force.div(root.getMass()));
    }
}
