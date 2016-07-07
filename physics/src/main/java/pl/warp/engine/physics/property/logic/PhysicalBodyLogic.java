package pl.warp.engine.physics.property.logic;

import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/7/16.
 */
public class PhysicalBodyLogic {

    private PhysicalBodyProperty root;


    public PhysicalBodyLogic(PhysicalBodyProperty root){

        this.root = root;
    }

    public void processAcceleration(){
        root.addSpeed(root.getForce().div(root.getMass()));
    }
}
