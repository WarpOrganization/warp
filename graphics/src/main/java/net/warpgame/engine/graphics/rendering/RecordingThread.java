package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.*;

/**
 * @author MarconZet
 * Created 12.01.2020
 */
@Service
@Profile("graphics")
@EnableConfig
@RegisterExecutor("recording")
public class RecordingThread extends SyncEngineThread {
    public RecordingThread(Config config) {
        super(new SyncTimer(config.getValue("graphics.vulkan.recording.ups")), new OnePerUpdateExecutionStrategy());
    }
}
