package pl.warp.engine.audio;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

/**
 * @author Hubertus
 *         Created 23.12.16
 */
public class Listener {
    private Component component;
    private Vector3f offset;

    public Listener(Component component, Vector3f offset) {
        this.component = component;
        this.offset = offset;
    }

    public Listener(Component component){
        this.component = component;
        offset = new Vector3f();
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Vector3f getOffset() {
        return offset;
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }
}
