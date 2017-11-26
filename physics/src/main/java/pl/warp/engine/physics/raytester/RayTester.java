package pl.warp.engine.physics.raytester;


import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.physics.PhysicsTask;

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
