package pl.warp.engine.graphics.particles;

import org.joml.Vector3f;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2017-03-09 at 22
 */
public class SpreadingParticleEmitter<T extends Particle> extends ParticleEmitter<T> {
    public static final float RANDOM_SCALE_THRESHOLD = 0.4f;

    private Vector3f initialVelocity;
    private Vector3f spread;
    private int timeToLive;
    private int timeToLiveGradient;
    private boolean randomizeScaleScalar;

    private Random random = new Random();

    public SpreadingParticleEmitter(int frequency, Vector3f initialVelocity, Vector3f spread, int timeToLive, int timeToLiveGradient, boolean randomizeScaleScalar) {
        super(frequency);
        this.initialVelocity = initialVelocity;
        this.spread = spread;
        this.timeToLive = timeToLive;
        this.timeToLiveGradient = timeToLiveGradient;
        this.randomizeScaleScalar = randomizeScaleScalar;
    }



    @Override
    public T newParticle(ParticleFactory<T> factory) {
        Vector3f position = new Vector3f();
        Vector3f velocity = generateVelocity();
        float scale = randomizeScaleScalar ? generateScale() : 1.0f;
        int timeToLive = (int) (this.timeToLive + (timeToLiveGradient * random.nextFloat() - (timeToLiveGradient / 2.0f)));
        return factory.newParticle(position, velocity, scale, 0.0f, timeToLive);
    }

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
