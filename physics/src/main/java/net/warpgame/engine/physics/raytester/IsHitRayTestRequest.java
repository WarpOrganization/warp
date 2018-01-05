package net.warpgame.engine.physics.raytester;

import org.joml.Vector3f;
import net.warpgame.engine.core.execution.Executor;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 28.09.2017
 */
public class IsHitRayTestRequest extends RayTestRequest {

    private Consumer<Boolean> consumer;

    public IsHitRayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, Consumer<Boolean> consumer) {
        super(startVector, endVector, executor, false);
        this.consumer = consumer;
    }

    public IsHitRayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, Consumer<Boolean> consumer, boolean executeIfNotHit) {
        super(startVector, endVector, executor, executeIfNotHit);
        this.consumer = consumer;
    }

    @Override
    public Consumer<Boolean> getConsumer() {
        return consumer;
    }

    @Override
    public int getType() {
        return RayTestRequest.IS_HIT;
    }

    public void setConsumer(Consumer<Boolean> consumer) {
        this.consumer = consumer;
    }
}
