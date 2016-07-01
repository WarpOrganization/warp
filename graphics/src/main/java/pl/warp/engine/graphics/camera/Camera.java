package pl.warp.engine.graphics.camera;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

import java.util.List;


/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class Camera extends Component {
    private Vector3f position;
    private Quaternionf rotation = new Quaternionf();

    public Camera(Component parent, Vector3f position) {
        super(parent);
        this.position = position;
    }

    public Camera(Component parent) {
        this(parent, new Vector3f());
    }

    public void move(Vector3f d) {
        position.add(d);
    }

    public void move(float dx, float dy, float dz) {
        position.add(dx, dy, dz);
    }

    public void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians) {
        rotation.rotate(angleXInRadians, angleYInRadians, angleZInRadians);
    }

    @Override
    public List<Component> getChildren() {
        return ImmutableList.of();
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    Quaternionf getRotation() {
        return rotation;
    }
}
