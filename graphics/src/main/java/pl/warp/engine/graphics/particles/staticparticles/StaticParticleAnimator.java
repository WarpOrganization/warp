package pl.warp.engine.graphics.particles.staticparticles;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.particles.Particle;
import pl.warp.engine.graphics.particles.ParticleAnimator;

/**
 * @author Jaca777
 *         Created 2017-03-21 at 20
 */
public class StaticParticleAnimator<T extends Particle> implements ParticleAnimator<T>{

    private final TransformProperty transform;
    private Vector3f lastPos = new Vector3f();
    private float boxEdge;

    public StaticParticleAnimator(Component component, float boxEdge) {
        this.transform = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        this.boxEdge = boxEdge;
        updateLastPos();
    }


    @Override
    public void animate(T particle, int delta) {
        reposition(particle);
    }

    private Vector3f temp = new Vector3f();

    private void reposition(T particle) {
        Vector3f position = particle.getPosition();
        position.sub(transform.getTranslation());
        //shifting particle pos to be always positive, then repositioning (with modulo), then un-shifting
        float x = ((position.x + boxEdge * 1.5f) % (boxEdge)) - boxEdge * 0.5f;
        float y = ((position.y + boxEdge * 1.5f) % (boxEdge)) - boxEdge * 0.5f;
        float z = ((position.z + boxEdge * 1.5f) % (boxEdge)) - boxEdge * 0.5f;
        position.set(x, y, z).add(transform.getTranslation());
    }

    @Override
    public void afterAnimate() {
        updateLastPos();
    }

    protected void updateLastPos() {
        this.lastPos.set(transform.getTranslation());
    }
}
