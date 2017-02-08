package pl.warp.test.ai;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.test.DroneProperty;

/**
 * @author Hubertus
 *         Created 05.02.17
 */
public class DroneMemoryProperty extends Property {
    public static final String DRONE_MEMORY_PROPERTY_NAME = "droneMemory";

    private Component target;
    private DroneProperty targetDroneProperty;

    public DroneMemoryProperty(){
        super(DRONE_MEMORY_PROPERTY_NAME);
    }

    public Component getTarget() {
        return target;
    }

    public void setTarget(Component target) {
        this.target = target;
    }

    public DroneProperty getTargetDroneProperty() {
        return targetDroneProperty;
    }

    public void setTargetDroneProperty(DroneProperty targetDroneProperty) {
        this.targetDroneProperty = targetDroneProperty;
    }
}
