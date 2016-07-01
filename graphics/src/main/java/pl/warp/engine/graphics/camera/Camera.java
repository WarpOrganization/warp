package pl.warp.engine.graphics.camera;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

import java.util.List;


/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public abstract class Camera extends Component {

    public Camera(Component parent) {
        super(parent);
    }

    public abstract void move(Vector3f v);

    public abstract void move(float dx, float dy, float dz);

    public abstract void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians);

    public abstract Vector3f getPosition();

    public abstract Matrix4f getCameraMatrix();

    public abstract Matrix4f getProjectionMatrix();

    @Override
    public List<Component> getChildren() {
        return ImmutableList.of();
    }

    @Override
    public boolean hasChildren() {
        return false;
    }
}
