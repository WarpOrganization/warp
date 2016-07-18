package pl.warp.engine.graphics.particles;

import pl.warp.engine.graphics.texture.Texture2DArray;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 14
 */
public class ParticleSystem {

    private LinkedList<Particle> particles = new LinkedList<>();
    private ParticleAnimator animator;
    private ParticleEmitter emitter;
    private Texture2DArray spriteSheet;

    public ParticleSystem(ParticleAnimator animator, ParticleFactory factory, float frequency, Texture2DArray spriteSheet) {
        this.animator = animator;
        this.emitter = new ParticleEmitter(factory, frequency, particles);
        this.spriteSheet = spriteSheet;
    }

    public void update(int delta) {
        updateParticleLifeTimes(delta);
        updateTextures();
        emitParticles(delta);
        animateParticles(delta);
    }

    private void updateParticleLifeTimes(int delta) {
        for (Iterator<Particle> iterator = particles.iterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();
            int ttl = particle.getTimeToLive() - delta;
            if (ttl > 0) particle.setTimeToLive(ttl);
            else iterator.remove();
        }
    }

    private void updateTextures() {
        for (Particle particle : particles) {
            int textureIndex = (particle.getTimeToLive() * spriteSheet.getSize()) / particle.getTotalTimeToLive();
            particle.setTextureIndex(textureIndex);
        }
    }

    private void emitParticles(int delta) {
        emitter.emit(delta);
    }

    private void animateParticles(int delta) {
        for (Particle particle : particles)
            animator.animate(particle, delta);
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void removeParticle(Particle particle) {
        particles.remove(particle);
    }

    public LinkedList<Particle> getParticles() {
        return particles;
    }

    public Texture2DArray getSpriteSheet() {
        return spriteSheet;
    }
}
