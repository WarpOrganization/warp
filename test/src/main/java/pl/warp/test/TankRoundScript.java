package pl.warp.test;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleSystem;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.particles.dot.RandomSpreadingStageDotParticleFactory;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.EventHandler;
import pl.warp.game.script.GameScript;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Hubertus
 *         Created 03.03.17
 */
public class TankRoundScript extends GameScript<GameComponent> {

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(40);


    public TankRoundScript(GameComponent owner) {
        super(owner);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {

    }

    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
    public synchronized void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (!component.hasEnabledProperty(GravityProperty.GRAVITY_PROPERTY_NAME)) {
            //TODO destroy tank
        }
        kaboom(getOwner());
        if (getOwner().hasParent())
            getOwner().destroy();
    }

    private void kaboom(GameComponent component) {
        ParticleAnimator animator1 = new SimpleParticleAnimator(new Vector3f(0), 0, 0);
        ParticleStage[] stages1 = {
                new ParticleStage(10.0f, new Vector4f(1.0f, 0.2f, 1.0f, 1.0f)),
                new ParticleStage(10.0f, new Vector4f(0.5f, 0.2f, 1.0f, 0.0f))
        };
        ParticleFactory<DotParticle> factory1 = new RandomSpreadingStageDotParticleFactory(new Vector3f(.04f), 500, 100, true, true, stages1);
        DotParticleSystem system1 = new DotParticleSystem(animator1, factory1, 400);
        ParticleEmitterProperty property = new ParticleEmitterProperty(system1);
        component.addProperty(property);
        executorService.schedule(() -> system1.setEmit(false), 300, TimeUnit.MILLISECONDS);
    }
}
