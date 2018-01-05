package net.warpgame.engine.physics.raytester;

import org.joml.Vector3f;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.execution.Executor;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 28.09.2017
 */
public class ClosestComponentHitRayTestRequest extends RayTestRequest {

    private Consumer<Component> consumer;

    public ClosestComponentHitRayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, Consumer<Component> consumer) {
        super(startVector, endVector, executor, false);
        this.consumer = consumer;
    }

    public ClosestComponentHitRayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, Consumer<Component> consumer, boolean executeIfNotHit) {
        super(startVector, endVector, executor, executeIfNotHit);
        this.consumer = consumer;
    }

    @Override
    public Consumer<Component> getConsumer() {
        return consumer;
    }

    @Override
    public int getType() {
        return RayTestRequest.CLOSEST_COMPONENT_HIT;
    }

    public void setConsumer(Consumer<Component> consumer) {
        this.consumer = consumer;
    }
}
