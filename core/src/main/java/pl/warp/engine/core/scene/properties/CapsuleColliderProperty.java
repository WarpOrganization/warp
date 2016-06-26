package pl.warp.engine.core.scene.properties;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by Hubertus on 2016-06-26.
 */
public class CapsuleColliderProperty extends Property<Component> {
    private double x;
    private double z;

    public CapsuleColliderProperty(Component owner, double x, double z) {
        super(owner);
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapsuleColliderProperty that = (CapsuleColliderProperty) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    public double getZ() {
        return z;
    }

    public double getX() {
        return x;
    }
}
