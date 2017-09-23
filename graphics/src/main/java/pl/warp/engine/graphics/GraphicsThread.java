package pl.warp.engine.graphics;

import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.execution.*;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
@Service
public class GraphicsThread extends SyncEngineThread{
    public GraphicsThread(RenderingConfig config) {
        super(new SyncTimer(config.ups), new RapidExecutionStrategy());
    }
}
