package pl.warp.engine.core.scene.properties;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by Hubertus on 2016-06-26.
 */
public class BoxColliderProperty extends Property<Component> {

    private double height;
    private double width;
    private double depth;


    public BoxColliderProperty(double height, double width, double depth) {
        super();
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoxColliderProperty that = (BoxColliderProperty) o;
        return Double.compare(that.height, height) == 0 &&
                Double.compare(that.width, width) == 0 &&
                Double.compare(that.depth, depth) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, depth);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() {
        return depth;
    }
}
