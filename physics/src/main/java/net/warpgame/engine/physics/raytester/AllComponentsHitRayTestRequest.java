package net.warpgame.engine.physics.raytester;

import org.joml.Vector3f;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.execution.Executor;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 28.09.2017
 */
public class AllComponentsHitRayTestRequest extends RayTestRequest {
    private Consumer<ArrayList<Component>> consumer;

    public AllComponentsHitRayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, Consumer<ArrayList<Component>> consumer) {
        super(startVector, endVector, executor, false);
        this.consumer = consumer;
    }

    public AllComponentsHitRayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, Consumer<ArrayList<Component>> consumer, boolean executeIfNotHit) {
        super(startVector, endVector, executor, executeIfNotHit);
        this.consumer = consumer;
    }

    @Override
    public Consumer<ArrayList<Component>> getConsumer() {
        return consumer;
    }

    @Override
    public int getType() {
        return RayTestRequest.ALL_COMPONENTS_HIT;
    }

    public void setConsumer(Consumer<ArrayList<Component>> consumer) {
        this.consumer = consumer;
    }
}
