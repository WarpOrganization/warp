package net.warpgame.engine.physics.raytester;


import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.physics.PhysicsTask;

/**
 * @author Hubertus
 * Created 24.09.16
 */
@Service
public class RayTester {

    private RayTestSolver rayTestSolver;


    public RayTester(PhysicsTask physicsTask) {
        rayTestSolver = physicsTask.getRayTestSolver();
    }

    public void rayTest(RayTestRequest request) {
        rayTestSolver.requestRayTest(request);
    }

}
