package pl.warp.engine.graphics.particles.textured;

import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.ParticleRendererFactory;
import pl.warp.engine.graphics.particles.ParticleSystemAttribute;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Jaca777
 *         Created 2017-03-10 at 21
 */
public class TexturedParticleAttribute implements ParticleSystemAttribute<TexturedParticle> {
    private Texture2DArray spritesheet;

    public TexturedParticleAttribute(Texture2DArray spritesheet) {
        this.spritesheet = spritesheet;
    }

    @Override
    public ParticleFactory<TexturedParticle> getParticleFactory() {
        return new TexturedParticleFactory(spritesheet);
    }

    @Override
    public ParticleRendererFactory<TexturedParticle> getParticleRendererFactory() {
        return TexturedParticleRenderer::new;
    }
}
