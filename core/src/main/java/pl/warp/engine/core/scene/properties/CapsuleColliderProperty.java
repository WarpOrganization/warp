package pl.warp.engine.core.scene.properties;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by Hubertus on 2016-06-26.
 */
public class CapsuleColliderProperty extends Property<Component> {
    private double radius;
    private double height;

    public CapsuleColliderProperty(double radius, double height) {
        super();
        this.radius = radius;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapsuleColliderProperty that = (CapsuleColliderProperty) o;
        return Double.compare(that.radius, radius) == 0 &&
                Double.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, height);
    }

    public double getHeight() {
        return height;
    }

    public double getRadius() {
        return radius;
    }
}
