package pl.warp.engine.physics.raytester;

import com.badlogic.gdx.math.Vector3;
import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;

/**
 * @author Hubertus
 * Created 01.08.16
 */

public class RayTestTask {
    private Vector3 startPos = new Vector3();
    private Vector3 endPos = new Vector3();
    private boolean hasHit;
    private Component hit;

    public RayTestTask(Vector3f startPos, Vector3f endPos) {
        setStartPos(startPos);
        setEndPos(endPos);
        hasHit = false;
    }

    public synchronized Vector3 getStartPos() {
        return startPos;
    }

    public synchronized void setStartPos(Vector3f startPos) {
        this.startPos.set(startPos.x, startPos.y, startPos.z);
    }

    public synchronized Vector3 getEndPos() {
        return endPos;
    }

    public synchronized void setEndPos(Vector3f endPos) {
        this.endPos.set(endPos.x, endPos.y, endPos.z);
    }

    public synchronized boolean isHasHit() {
        return hasHit;
    }

    public synchronized void setHasHit(boolean hasHit) {
        this.hasHit = hasHit;
    }

    public synchronized Component getHit() {
        return hit;
    }

    public synchronized void setHit(Component hit) {
        this.hit = hit;
    }
}
