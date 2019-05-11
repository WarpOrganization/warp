package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.camera.CameraProperty;
import org.joml.Matrix4fc;

/**
 * @author MarconZet
 * Created 15.08.2018
 */
public class CanvasProperty extends Property {
    private Matrix4fc projectionMatrix;
    private CameraProperty cameraProperty;

    public CanvasProperty(CameraProperty cameraProperty) {
        this.cameraProperty = cameraProperty;
    }

    @Override
    public void init() {
        projectionMatrix = cameraProperty.getUiProjectionMatrix();
    }

    public Matrix4fc getProjectionMatrix() {
        return projectionMatrix;
    }

    public CameraProperty getCameraProperty() {
        return cameraProperty;
    }
}
