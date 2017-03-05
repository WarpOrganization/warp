package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 15
 */
public abstract class RandomSpreadingParticleFactory<T extends Particle> implements ParticleFactory<T> {

    public static final float RANDOM_SCALE_THRESHOLD = 0.4f;

    private Vector3f initialVelocity;
    private Vector3f spread;
    private int timeToLive;
    private int timeToLiveGradient;
    private boolean randomizeRotation;
    private boolean randomizeScaleScalar;

    private Random random = new Random();

    public RandomSpreadingParticleFactory(Vector3f initialVelocity, Vector3f spread, int timeToLive, int timeToLiveGradient, boolean randomizeRotation, boolean randomizeScaleScalar) {
        this.initialVelocity = initialVelocity;
        this.spread = spread;
        this.timeToLive = timeToLive;
        this.timeToLiveGradient = timeToLiveGradient;
        this.randomizeRotation = randomizeRotation;
        this.randomizeScaleScalar = randomizeScaleScalar;
    }

    @Override
    public T newParticle() {
        Vector3f position = new Vector3f();
        Vector3f velocity = generateVelocity();
        float scale = randomizeScaleScalar ? generateScale() : 1.0f;
        float rotation = randomizeRotation ? generateRotation() : 0;
        int timeToLive = (int) (this.timeToLive + (timeToLiveGradient * random.nextFloat() - (timeToLiveGradient / 2.0f)));
        return newParticle(position, velocity, scale, rotation, timeToLive);
    }

    public abstract T newParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int ttl);


    protected Vector3f generateVelocity() {
        float x = random.nextFloat() * 2 - 1;
        float y = random.nextFloat() * 2 - 1;
        float z = random.nextFloat() * 2 - 1;
        return new Vector3f(x, y, z).normalize().mul(spread).add(initialVelocity);
    }

    protected float generateScale() {
        float delta = RANDOM_SCALE_THRESHOLD * random.nextFloat();
        return 1.0f - RANDOM_SCALE_THRESHOLD / 2 + delta;
    }

    protected float generateRotation() {
        return (float) (2 * Math.PI * random.nextFloat());
    }

}
