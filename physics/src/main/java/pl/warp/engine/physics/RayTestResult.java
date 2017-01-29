package pl.warp.engine.physics;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

/**
 * @author Hubertus
 *         Created 28.01.17
 */
public class RayTestResult {
    private Component componentHit;
    private boolean hasHit;
    private Vector3f hitLocation;

    public Component getComponentHit() {
        return componentHit;
    }

    public void setComponentHit(Component componentHit) {
        this.componentHit = componentHit;
    }

    public boolean isHasHit() {
        return hasHit;
    }

    public void setHasHit(boolean hasHit) {
        this.hasHit = hasHit;
    }

    public Vector3f getHitLocation() {
        return hitLocation;
    }

    public void setHitLocation(Vector3f hitLocation) {
        this.hitLocation = hitLocation;
    }
}
