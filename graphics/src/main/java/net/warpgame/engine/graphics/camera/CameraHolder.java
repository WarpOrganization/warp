package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
@Profile("graphics")
public class CameraHolder {
    private Camera camera;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
