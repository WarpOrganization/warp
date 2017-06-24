package pl.warp.engine.game.graphics.effects.planet.generator.surface;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2017-04-09 at 13
 */
public class Biome {
    private float pos;
    private float height;
    private Vector3f color;

    public Biome(float pos, float height, Vector3f color) {
        this.pos = pos;
        this.height = height;
        this.color = color;
    }

    public float getPos() {
        return pos;
    }

    public Biome setPos(float pos) {
        this.pos = pos;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public Biome setHeight(float height) {
        this.height = height;
        return this;
    }

    public Vector3f getColor() {
        return color;
    }

    public Biome setColor(Vector3f color) {
        this.color = color;
        return this;
    }
}
