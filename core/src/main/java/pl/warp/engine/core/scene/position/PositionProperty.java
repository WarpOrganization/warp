package pl.warp.engine.core.scene.position;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.observable.ObservableProperty;
import pl.warp.engine.core.scene.observable.ObservableValue;

/**
 * @author Jaca777
 *         Created 2016-12-26 at 21
 */
public class PositionProperty extends ObservableProperty<Component> {
    public static final String POSITION_PROPERTY_NAME = "PositionProperty";
    private final int unit;
    private ObservableValue<Vector3f> position;
    private PositionCalculationStrategy strategy;

    public PositionProperty(ObservableValue<Vector3f> observablePosition, PositionCalculationStrategy strategy, int unit) {
        super(POSITION_PROPERTY_NAME, observablePosition);
        this.strategy = strategy;
        this.position = observablePosition;
        this.unit = unit;
    }

    public PositionProperty(Vector3f initialPosition, PositionCalculationStrategy strategy, int unit) {
        this(ObservableValue.of(initialPosition), strategy, unit);
    }

    @Override
    public void enable() {
        super.enable();
        strategy.initialize(getOwner());
    }

    public Vector3f getPosition() {
        return position.getValue();
    }

    public void setPosition(Vector3f pos) {
        position.setValue(pos);
    }

    public int getUnit() {
        return unit;
    }

    public PositionCalculationStrategy getStrategy() {
        return strategy;
    }
}
