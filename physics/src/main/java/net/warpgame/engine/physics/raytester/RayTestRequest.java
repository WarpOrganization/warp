package net.warpgame.engine.physics.raytester;

import com.badlogic.gdx.math.Vector3;
import org.joml.Vector3f;
import net.warpgame.engine.core.execution.Executor;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 25.09.2017
 */
public abstract class RayTestRequest {

    static final int CLOSEST_COMPONENT_HIT = 1;
    static final int ALL_COMPONENTS_HIT = 2;
    static final int IS_HIT = 3;

    private Vector3 startVector = new Vector3();
    private Vector3 endVector = new Vector3();
    private Executor executor;
    private boolean executeIfNotHit;

    public RayTestRequest(Vector3f startVector, Vector3f endVector, Executor executor, boolean executeIfNotHit) {
        this.startVector.set(startVector.x, startVector.y, startVector.z);
        this.endVector.set(endVector.x, endVector.y, endVector.z);
        this.executor = executor;
        this.executeIfNotHit = executeIfNotHit;
    }

    public abstract Consumer getConsumer();
    public abstract int getType();


    public Vector3 getStartVector() {
        return startVector;
    }

    public void setStartVector(Vector3f startVector) {
        this.startVector.set(startVector.x, startVector.y, startVector.z);
    }

    public Vector3 getEndVector() {
        return endVector;
    }

    public void setEndVector(Vector3f endVector) {
        this.endVector.set(endVector.x, endVector.y, endVector.z);
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public boolean isExecuteIfNotHit() {
        return executeIfNotHit;
    }

    public void setExecuteIfNotHit(boolean executeIfNotHit) {
        this.executeIfNotHit = executeIfNotHit;
    }

}
