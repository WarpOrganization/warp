package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

/**
 * @author MarconZet
 * Created 12.01.2020
 */

@Service
@Profile("graphics")
@EnableConfig
@RegisterExecutor("render")
public class RenderThread extends SyncEngineThread {
    public RenderThread(Config config) {
        super(new SyncTimer(config.getValue("graphics.vulkan.render.ups")), new RapidExecutionStrategy());
    }
}
