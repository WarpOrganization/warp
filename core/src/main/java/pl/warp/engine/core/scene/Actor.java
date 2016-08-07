package pl.warp.engine.core.scene;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 16
 */
public abstract class Actor extends Component {
    public Actor(Component parent) {
        super(parent);
    }

    public abstract Vector3f getPosition(Vector3f dest);
}
