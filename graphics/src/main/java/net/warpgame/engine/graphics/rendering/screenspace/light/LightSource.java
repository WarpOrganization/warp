package net.warpgame.engine.graphics.rendering.screenspace.light;

import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2017-11-18 at 21
 */
public class LightSource {
    private Vector3f color;

    public LightSource(Vector3f color) {
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
