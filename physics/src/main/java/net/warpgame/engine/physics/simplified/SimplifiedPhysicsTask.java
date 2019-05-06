package net.warpgame.engine.physics.simplified;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;

/**
 * @author Hubertus
 * Created 07.01.2018
 */
@Service
@RegisterTask(thread = "physics")
@Profile("physics")
public class SimplifiedPhysicsTask extends EngineTask {


    private SimplifiedPhysicsSimulationService simulationService;

    public SimplifiedPhysicsTask(SimplifiedPhysicsSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        simulationService.update(delta);
    }
}
