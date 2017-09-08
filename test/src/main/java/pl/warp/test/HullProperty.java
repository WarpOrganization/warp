package pl.warp.test;

import pl.warp.engine.core.property.Property;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class HullProperty extends Property {
    public static final String HULL_PROPERTY_NAME = "hullProperty";
    
    private GameComponent spinningWheel;
    private GameComponent tracks;
    private float acceleration;
    private float rotationSpeed;
    private float maxSpeed;
    private float brakingForce;

    public HullProperty(
            GameComponent spinningWheel, 
            GameComponent tracks, 
            float acceleration, 
            float rotationSpeed, 
            float maxSpeed, 
            float brakingForce
    ) {
        super(HULL_PROPERTY_NAME);
        this.spinningWheel = spinningWheel;
        this.tracks = tracks;
        this.acceleration = acceleration;
        this.rotationSpeed = rotationSpeed;
        this.maxSpeed = maxSpeed;
        this.brakingForce = brakingForce;
    }
    

    public GameComponent getSpinningWheel() {
        return spinningWheel;
    }

    public HullProperty setSpinningWheel(GameComponent spinningWheel) {
        this.spinningWheel = spinningWheel;
        return this;
    }

    public GameComponent getTracks() {
        return tracks;
    }

    public HullProperty setTracks(GameComponent tracks) {
        this.tracks = tracks;
        return this;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public HullProperty setAcceleration(float acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public HullProperty setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
        return this;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public HullProperty setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
        return this;
    }

    public float getBrakingForce() {
        return brakingForce;
    }

    public HullProperty setBrakingForce(float brakingForce) {
        this.brakingForce = brakingForce;
        return this;
    }
}
