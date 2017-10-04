package pl.warp.engine.physics.raytester;

import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.physics.ColliderComponentRegistry;
import pl.warp.engine.physics.PhysicsWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 24.09.2017
 */
public class RayTestSolver {

    private List<RayTestRequest> rayTestRequests = new ArrayList<>();
    private ClosestRayResultCallback result;
    private ClosestRayResultCallback result2; //used only by physics thread
    private PhysicsWorld world;
    private ColliderComponentRegistry colliderComponentRegistry;

    public RayTestSolver(ColliderComponentRegistry colliderComponentRegistry) {
        this.colliderComponentRegistry = colliderComponentRegistry;
    }


    synchronized void requestRayTest(RayTestRequest rayTestRequest) {
        rayTestRequests.add(rayTestRequest);
    }

    public synchronized void update() {
        for (RayTestRequest request : rayTestRequests) {
            clearResult(result2);
            result2.setRayFromWorld(request.getStartVector());
            result2.setRayToWorld(request.getEndVector());

            world.getDynamicsWorld().rayTest(request.getStartVector(), request.getEndVector(), result2);
            if (result2.hasHit()) {
                switch (request.getType()) {
                    case RayTestRequest.ALL_COMPONENTS_HIT:
                        ArrayList<Component> componentsHit = new ArrayList<Component>();
                        //TODO
                        break;
                    case RayTestRequest.CLOSEST_COMPONENT_HIT:
                        Component c = colliderComponentRegistry.getCompoenent(result2.getCollisionObject().getUserValue());
                        request.getConsumer().accept(c);
                        break;
                    case RayTestRequest.IS_HIT:
                        request.getConsumer().accept(true);
                        break;
                }

            } else {
                if (request.isExecuteIfNotHit()) {
                    if (request.getType() == RayTestRequest.IS_HIT)
                        request.getConsumer().accept(false);
                    else request.getConsumer().accept(null);
                }
            }
        }

        rayTestRequests.clear();
    }

    private void clearResult(ClosestRayResultCallback result) {
        result.setCollisionObject(null);
        result.setClosestHitFraction(1f);
    }

    public void setWorld(PhysicsWorld world) {
        this.world = world;
    }
}
