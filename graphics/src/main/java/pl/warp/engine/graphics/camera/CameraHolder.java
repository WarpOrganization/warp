package pl.warp.engine.graphics.camera;

import pl.warp.engine.core.context.annotation.Service;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
public class CameraHolder {
    private Camera camera;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
