package pl.warp.engine.core.scene.position;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2016-12-28 at 21
 */
public abstract class PositionCalculationStrategy {
    public abstract void initialize(PositionProperty positionProperty);
    public abstract Vector3f getPosition(Component parentToRelate, int unit, Vector3f dest);
}
