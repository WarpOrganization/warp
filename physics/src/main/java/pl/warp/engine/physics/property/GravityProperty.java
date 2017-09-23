package pl.warp.engine.physics.property;

import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Hubertus
 *         Created 05.08.16
 */
public class GravityProperty extends Property {

    public static final String GRAVITY_PROPERTY_NAME = "gravityBodyProperty";

    private Vector3f downVector;

    private boolean isStanding;

    public GravityProperty(Vector3f downVector) {
        super(GRAVITY_PROPERTY_NAME);
        this.downVector = downVector;
    }

    public Vector3f getDownVector() {
        return downVector;
    }

    public void setDownVector(Vector3f downVector) {
        this.downVector = downVector;
    }

    public boolean isStanding() {
        return isStanding;
    }

    public void setStanding(boolean standing) {
        isStanding = standing;
    }
}
