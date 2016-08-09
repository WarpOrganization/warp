package pl.warp.engine.graphics.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public class RandomSpreadingTexturedParticleFactory implements ParticleFactory<TexturedParticle>  {

    public static final float RANDOM_SCALE_THRESHOLD = 0.4f;

    private float velocity;
    private int timeToLive;
    private boolean randomizeRotation;
    private boolean randomizeScaleScalar;

    private Random random = new Random();

    public RandomSpreadingTexturedParticleFactory(float velocity, int timeToLive, boolean randomizeRotation, boolean randomizeScaleScalar) {
        this.velocity = velocity;
        this.timeToLive = timeToLive;
        this.randomizeRotation = randomizeRotation;
        this.randomizeScaleScalar = randomizeScaleScalar;
    }

    @Override
    public TexturedParticle newParticle() {
        Vector3f position = new Vector3f();
        Vector3f velocity = genVelocity();
        Vector2f scale = randomizeScaleScalar ? genScale() : new Vector2f(1);
        float rotation = randomizeRotation ? genRotation() : 0;
        return new TexturedParticle(position, velocity, scale, rotation, timeToLive, timeToLive, timeToLive);
    }


    private Vector3f genVelocity() {
        float x = random.nextFloat() * 2 - 1;
        float y = random.nextFloat() * 2 - 1;
        float z = random.nextFloat() * 2 - 1;
        return new Vector3f(x, y, z).normalize().mul(velocity);
    }

    private Vector2f genScale() {
        float delta = RANDOM_SCALE_THRESHOLD * random.nextFloat();
        float scale = 1.0f - RANDOM_SCALE_THRESHOLD / 2 + delta;
        return new Vector2f(scale);
    }

    private float genRotation() {
        return (float) (2 * Math.PI * random.nextFloat());
    }

}
