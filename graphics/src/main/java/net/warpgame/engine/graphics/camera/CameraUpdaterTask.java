package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.SimpleEngineTask;

/**
 * @author Jaca777
 * Created 2017-09-23 at 23
 */
@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class CameraUpdaterTask extends SimpleEngineTask {

    private CameraHolder cameraHolder;

    public CameraUpdaterTask(CameraHolder cameraHolder) {
        this.cameraHolder = cameraHolder;
    }

    @Override
    public void update(int delta) {
        CameraProperty camera = this.cameraHolder.getCameraProperty();
        if(camera != null) camera.update();
    }
}
