package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.OnePerUpdateExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

/**
 * @author MarconZet
 * Created 11.01.2020
 */
@Service
@Profile("graphics")
@EnableConfig
@RegisterExecutor("loading")
public class VulkanLoadThread extends SyncEngineThread {
    public VulkanLoadThread(Config config) {
        super(new SyncTimer(config.getValue("graphics.vulkan.loading.ups")), new OnePerUpdateExecutionStrategy());
    }
}
