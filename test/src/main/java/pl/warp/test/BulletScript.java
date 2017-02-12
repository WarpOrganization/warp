package pl.warp.test;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleSystem;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.particles.dot.RandomSpreadingStageDotParticleFactory;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.EventHandler;
import pl.warp.game.script.GameScript;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Hubertus
 *         Created 7/12/16
 */

public class BulletScript extends GameScript<GameComponent> {

    private int life;
    private Listener<Component, CollisionEvent> collisionListener;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
    private Texture2DArray explosionSpritesheet;
    private Component shooterShip;

    public BulletScript(GameComponent owner, int life, Texture2DArray explosionSpritesheet, GameComponent playerShip) {
        super(owner);
        this.life = life;
        this.explosionSpritesheet = explosionSpritesheet;
        this.shooterShip = playerShip;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {
        life -= delta;
        if (life < 0)
            if (getOwner().hasParent())
                getOwner().destroy(); // We are not mean, we won't kill orphans. Nobility quickfix.
    }

    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
    private synchronized void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (component.hasEnabledProperty(Bulletproof.class)) return;
        if (component != shooterShip && component.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) {
            component.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).disable();
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
            component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME).disable();
            component.forEachChildren(c -> {
                if (c.hasProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME))
                    c.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME).disable();
            });
            component.addProperty(new Bulletproof());
            kaboom(component);
            DroneProperty droneProperty = component.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
            droneProperty.setHitPoints(droneProperty.getHitPoints() - 1);
            executorService.schedule(() -> destroy(component), 2, TimeUnit.SECONDS);
        }
    }

    private void resetComponent(Component component) {
        component.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).enable();
        component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME).enable();
        component.forEachChildren(c -> {
            if (c.hasProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME))
                c.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME).enable();
        });
        TransformProperty transform = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        DroneProperty droneProperty = component.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
        GameComponent respawn = droneProperty.getRespawn();
        Transforms.getAbsolutePosition(respawn, transform.getTranslation());
        PhysicalBodyProperty bodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        bodyProperty.setVelocity(new Vector3f(0));
        bodyProperty.setAngularVelocity(new Vector3f(0));
        executorService.schedule(() -> {
            bodyProperty.setVelocity(new Vector3f(0));
            bodyProperty.setAngularVelocity(new Vector3f(0));
            transform.getRotation().set(0, 0, 0, 1);
        }, 50, TimeUnit.MILLISECONDS);
        if (!component.hasProperty(Bulletproof.class)) component.addProperty(new Bulletproof());
        Property bulletproofProperty = component.getProperty(Bulletproof.class);
        bulletproofProperty.enable();
        GraphicsMaterialProperty materialProperty = component.getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME);
        materialProperty.getMaterial().setTransparency(0.5f);
        executorService.schedule(() -> {
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).enable();
            materialProperty.getMaterial().setTransparency(1.0f);
            bulletproofProperty.disable();
        }, 5, TimeUnit.SECONDS);
    }


    private void kaboom(Component component) {
        ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0), 0, 0);
        ParticleStage[] stages = {
                new ParticleStage(0.8f, new Vector4f(2.5f, 0.5f, 1.5f, 2.0f)),
                new ParticleStage(0.8f, new Vector4f(1.0f, 0.5f, 1.0f, 0.0f))
        };
        ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(new Vector3f(.04f), 500, 0, true, true, stages);
        DotParticleSystem system = new DotParticleSystem(animator, factory, 1000);
        component.addProperty(new ParticleEmitterProperty(system));
        executorService.schedule(() -> system.setEmit(false), 300, TimeUnit.MILLISECONDS);
    }

    private void destroy(Component componentHit) {
        resetComponent(componentHit);
        getOwner().destroy();
    }

    //TODO REMOVE AS SOON AS DISABLING COLLIDER WORKS
    private static class Bulletproof extends Property {
        public static final String BULLETPROOF_PROPERTY_NAME = "bulletproof";

        public Bulletproof() {
            super(BULLETPROOF_PROPERTY_NAME);
        }
    }

}
