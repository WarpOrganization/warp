package pl.warp.engine.graphics.particles.textured;

import pl.warp.engine.graphics.particles.*;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;
import pl.warp.engine.graphics.texture.Texture2DArray;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 14
 */
public class TexturedParticleSystem extends ParticleSystem {


    private LinkedList<TexturedParticle> particles;
    private Texture2DArray spriteSheet;

    public TexturedParticleSystem(ParticleAnimator animator, ParticleFactory<TexturedParticle> factory, float frequency, Texture2DArray spriteSheet) {
        this(animator, factory, frequency, spriteSheet, new LinkedList<>());
    }

    public TexturedParticleSystem(ParticleAnimator animator, ParticleFactory<TexturedParticle> factory, float frequency,
                                  Texture2DArray spriteSheet, LinkedList<TexturedParticle> particles) {
        super(new ParticleEmitter<>(factory, frequency, particles), animator);
        this.spriteSheet = spriteSheet;
        this.particles = particles;
    }

    @Override
    public void onUpdate(int delta) {
        for (TexturedParticle particle : particles) {
            int textureIndex = (particle.getTimeToLive() * spriteSheet.getSize()) / particle.getTotalTimeToLive();
            particle.setTextureIndex(textureIndex);
        }
    }

    @Override
    public List<TexturedParticle> getParticles() {
        return particles;
    }

    @Override
    public ParticleType getParticleType() {
        return ParticleType.TEXTURED;
    }

    public Texture2DArray getSpriteSheet() {
        return spriteSheet;
    }
}
