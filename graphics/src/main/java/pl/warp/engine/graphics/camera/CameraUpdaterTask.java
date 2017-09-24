package pl.warp.engine.graphics.camera;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.SimpleEngineTask;

/**
 * @author Jaca777
 * Created 2017-09-23 at 23
 */
@Service
@RegisterTask(thread = "graphics")
public class CameraUpdaterTask extends SimpleEngineTask {

    private CameraHolder cameraHolder;

    public CameraUpdaterTask(CameraHolder cameraHolder) {
        this.cameraHolder = cameraHolder;
    }

    @Override
    public void update(int delta) {
        Camera camera = this.cameraHolder.getCamera();
        if(camera != null) camera.update(delta);
    }
}
