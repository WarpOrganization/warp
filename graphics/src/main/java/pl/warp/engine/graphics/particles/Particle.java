package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 14
 */
public class Particle {
    private Vector3f position;
    private Vector3f velocity;
    private Vector3f scale;
    private Vector3f rotation;
    private int timeToLive;

    public Particle(Vector3f position, Vector3f velocity, Vector3f scale, Vector3f rotation, int timeToLive) {
        this.position = position;
        this.velocity = velocity;
        this.scale = scale;
        this.rotation = rotation;
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

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }
}
