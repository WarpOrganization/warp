package pl.warp.engine.graphics.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Objects;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 14
 */
public abstract class Particle  {
    private Vector3f position;
    private Vector3f velocity;
    private Vector2f scale;
    private float rotation;
    private final int totalTimeToLive;
    private int timeToLive;

    public Particle(Vector3f position, Vector3f velocity, Vector2f scale, float rotation, int totalTimeToLive, int timeToLive) {
        this.position = position;
        this.velocity = velocity;
        this.scale = scale;
        this.rotation = rotation;
        this.totalTimeToLive = totalTimeToLive;
        this.timeToLive = timeToLive;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getTotalTimeToLive() {
        return totalTimeToLive;
    }
}
