package pl.warp.engine.graphics.camera;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 20
 */
public class CameraProperty extends Property<Component> {

    public static final String CAMERA_PROPERTY_NAME = "camera";

    private Camera camera;

    public CameraProperty(Camera camera) {
        super(CAMERA_PROPERTY_NAME);
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }
}
