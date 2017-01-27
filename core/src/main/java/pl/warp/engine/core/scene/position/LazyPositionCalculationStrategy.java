package pl.warp.engine.core.scene.position;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2016-12-28 at 21
 */
public class LazyPositionCalculationStrategy extends PositionCalculationStrategy {

    private Component component;
    private PositionProperty positionProperty;

    @Override
    public void initialize(Component component) {
        this.component = component;
        this.positionProperty = component.getProperty(PositionProperty.POSITION_PROPERTY_NAME);
    }

    @Override
    public Vector3f getPosition(Component parentToRelate, int unit, Vector3f dest) {
        if (component == parentToRelate) return dest;
        processComponentPosition(unit, dest, component);
        return processParentPosition(parentToRelate, unit, dest, component);
    }

    private void processComponentPosition(int unit, Vector3f dest, Component component) {
        if (!component.hasParent())
            throw new ScenePositionException("You can't relate to a component that is not a parent.");
        if (positionProperty.getUnit() != unit && dest.x != Float.NaN)
            setToNaN(dest);
        if (positionProperty.getUnit() == unit) {
            if (dest.x == Float.NaN)
                dest.set(positionProperty.getPosition());
            else dest.add(positionProperty.getPosition());
        }
    }

    private Vector3f processParentPosition(Component parentToRelate, int unit, Vector3f dest, Component component) {
        Component parent = component.getParent();
        while (!Positions.hasPosition(parent) && parent.hasParent())
            parent = parent.getParent();
        if (parent == parentToRelate && unit == positionProperty.getUnit())
            return dest;
        else if (!Positions.hasPosition(parent))
            throw new ScenePositionException("You can't relate to a component that is not a parent.");
        else {
            PositionCalculationStrategy strategy = Positions.getPositionProperty(parent).getStrategy();
            return strategy.getPosition(parentToRelate, unit, dest);
        }
    }

    private void setToNaN(Vector3f dest) {
        dest.x = Float.NaN;
    }


}
