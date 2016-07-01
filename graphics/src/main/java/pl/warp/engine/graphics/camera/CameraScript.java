package pl.warp.engine.graphics.camera;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Script;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class CameraScript extends Script<Camera> {
    
    public CameraScript(Camera owner) {
        super(owner);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onUpdate(long delta) {

    }

    public void lookAt(Vector3f vector3f) {
        //TODO
    }
}
