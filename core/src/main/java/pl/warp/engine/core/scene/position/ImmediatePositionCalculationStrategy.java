package pl.warp.engine.core.scene.position;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.observable.ObservablePropertyChangedEvent;

/**
 * @author Jaca777
 *         Created 2016-12-28 at 21
 */
public class ImmediatePositionCalculationStrategy extends PositionCalculationStrategy {
    @Override
    public void initialize(Component component) {
        SimpleListener.createListener(component, //todo observe parents (wtf how)
                ObservablePropertyChangedEvent.OBSERVABLE_PROPERTY_CHANGED_EVENT_NAME,
                this::propertyChanged);
    }

    private void propertyChanged(ObservablePropertyChangedEvent<Vector3f> event){
        Vector3f newPos = event.getChangedValue().getValue();
    }

    @Override
    public Vector3f getPosition(Component parentToRelate, int unit, Vector3f dest) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
