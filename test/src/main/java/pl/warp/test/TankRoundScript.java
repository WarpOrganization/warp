package pl.warp.test;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.graphics.particles.ParticleEmitter;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleSystem;
import pl.warp.engine.graphics.particles.SpreadingParticleEmitter;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleAttribute;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.core.script.EventHandler;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.OwnerProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Hubertus
 *         Created 03.03.17
 */
public class TankRoundScript extends Script {

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(40);


    @OwnerProperty(name = PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)
    private PhysicalBodyProperty body;

    public TankRoundScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {

    }

    private int timer = -1;

    @Override
    public void onUpdate(int delta) {
        if (timer > -1) timer += delta;
        if (timer > 1000)
            if (getOwner().hasParent())
                getOwner().destroy();

    }

    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
    public synchronized void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (!component.hasEnabledProperty(GravityProperty.GRAVITY_PROPERTY_NAME)) {
            //TODO destroy tank
        }
        body.setVelocity(new Vector3f(0));
        kaboom((GameComponent) getOwner());
        timer = 0;
    }

    private void kaboom(GameComponent component) {
        ParticleStage[] stages = {
                new ParticleStage(1.5f, new Vector4f(1.0f, 0.6f, 0.5f, 1.0f)),
                new ParticleStage(1.5f, new Vector4f(1.0f, 0.6f, 0.3f, 0.0f))
        };
        ParticleEmitter<DotParticle> emitter = new SpreadingParticleEmitter<>(400, new Vector3f(0), new Vector3f(.08f), 300, 100, true);
        ParticleSystem<DotParticle> system = new ParticleSystem<>(new DotParticleAttribute(stages), emitter);
        component.addProperty(new ParticleEmitterProperty(system));
        executorService.schedule(() -> system.setEmit(false), 200, TimeUnit.MILLISECONDS);
    }
}
