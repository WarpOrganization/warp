package pl.warp.test;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.*;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.particles.GraphicsParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.textured.RandomSpreadingTexturedParticleFactory;
import pl.warp.engine.graphics.particles.textured.TexturedParticle;
import pl.warp.engine.graphics.particles.textured.TexturedParticleSystem;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Hubertus
 *         Created 7/12/16
 */
public class BulletScript extends Script<Component> {

    private int life;
    private Listener<Component, CollisionEvent> collisionListener;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
    private Texture2DArray explosionSpritesheet;
    private Component playerShip;

    public BulletScript(Component owner, int life, Texture2DArray explosionSpritesheet, Component playerShip) {
        super(owner);
        this.life = life;
        this.explosionSpritesheet = explosionSpritesheet;
        this.playerShip = playerShip;
    }

    @Override
    public void onInit() {
        collisionListener = SimpleListener.createListener(getOwner(), CollisionEvent.COLLISION_EVENT_NAME, this::onCollision);
    }

    @Override
    public void onUpdate(int delta) {
        life -= delta;
        if (life < 0)
            getOwner().destroy();
    }

    private void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (component != playerShip && !component.hasProperty(DupaProperty.class)) {
            ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0), 0, 0);
            ParticleFactory<TexturedParticle> factory = new RandomSpreadingTexturedParticleFactory(0.04f, 300, true, true);
            component.getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).disable();
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
            component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME).disable();
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
            component.addProperty(new DupaProperty());
            TexturedParticleSystem system = new TexturedParticleSystem(animator, factory, 1000, explosionSpritesheet);
            component.addProperty(new GraphicsParticleEmitterProperty(system));
            executorService.schedule(() -> system.setEmit(false), 200, TimeUnit.MILLISECONDS);
            DroneProperty droneProperty = component.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
            droneProperty.setHitPoints(droneProperty.getHitPoints() - 1);
            executorService.schedule(() -> destroy(component), 1, TimeUnit.SECONDS);
        }
    }

    private void destroy(Component componentHit) {
        componentHit.destroy();
        getOwner().destroy();
    }

    //TODO REMOVE AS SOON AS DISABLING COLLIDER WORKS
    private static class DupaProperty extends Property {//xd

        public DupaProperty() {
            super();
        }
    }
}
