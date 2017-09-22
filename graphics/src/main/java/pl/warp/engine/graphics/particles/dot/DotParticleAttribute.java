package pl.warp.engine.graphics.particles.dot;

import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.ParticleSystemAttribute;

/**
 * @author Jaca777
 *         Created 2017-03-10 at 21
 */
public class DotParticleAttribute implements ParticleSystemAttribute<DotParticle> {
    private ParticleStage[] stages;

    public DotParticleAttribute(ParticleStage[] stages) {
        this.stages = stages;
    }

    @Override
    public ParticleFactory<DotParticle> getParticleFactory() {
        return new DotParticleFactory(stages);
    }

/*    @Override
    public ParticleRendererFactory<DotParticle> getParticleRendererFactory() {
        return DotParticleRenderer::new;
    }*/
}
