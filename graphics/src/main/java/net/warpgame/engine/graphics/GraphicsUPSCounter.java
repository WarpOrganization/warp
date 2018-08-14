package net.warpgame.engine.graphics;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.UPSCounterTask;

/**
 * @author Jaca777
 * Created 2017-09-24 at 01
 */

@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class GraphicsUPSCounter extends UPSCounterTask {
    public GraphicsUPSCounter(Config config) {
        super(
                config.getValue("graphics.upscounter.sampleSize"),
                config.getValue("graphics.upscounter.enabled")
        );
    }
}
