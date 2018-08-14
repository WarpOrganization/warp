package net.warpgame.engine.audio;


import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

@Service
@Profile("sound")
@EnableConfig
@RegisterExecutor("audio")
public class AudioThread extends SyncEngineThread {
    public AudioThread() {
        super(new SyncTimer(60), new RapidExecutionStrategy());
    }
}
