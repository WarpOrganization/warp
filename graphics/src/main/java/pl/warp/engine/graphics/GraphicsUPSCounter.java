package pl.warp.engine.graphics;

import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.service.Profile;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.UPSCounterTask;

/**
 * @author Jaca777
 * Created 2017-09-24 at 01
 */

@Service
@Profile("dev")
@RegisterTask(thread = "graphics")
public class GraphicsUPSCounter extends UPSCounterTask {
    public GraphicsUPSCounter(Config config) {
        super(
                config.getValue("graphics.upscounter.sampleSize"),
                config.getValue("graphics.upscounter.enabled")
        );
    }
}
