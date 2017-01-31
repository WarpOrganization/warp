package pl.warp.test;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Listener;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
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
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.script.GameScript;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * @author Hubertus
 *         Created 7/12/16
 */
public class BulletScript extends GameScript<GameComponent> {

    private int life;
    private Listener<Component, CollisionEvent> collisionListener;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
    private Texture2DArray explosionSpritesheet;
    private Component playerShip;

    public BulletScript(GameComponent owner, int life, Texture2DArray explosionSpritesheet, GameComponent playerShip) {
        super(owner);
        this.life = life;
        this.explosionSpritesheet = explosionSpritesheet;
        this.playerShip = playerShip;
    }

    @Override
    protected void init() {
        collisionListener = SimpleListener.createListener(getOwner(), CollisionEvent.COLLISION_EVENT_NAME, this::onCollision);
    }

    @Override
    protected void update(int delta) {
        life -= delta;
        if (life < 0)
            getOwner().destroy();
    }

    private synchronized void onCollision(CollisionEvent event) {
        Component component = event.getSecondComponent();
        if (component.hasEnabledProperty(Bulletproof.class)) return;
        if (component != playerShip && component != TestSceneLoader.MAIN_GOAT) {
            component.getProperty(GraphicsMeshProperty.MESH_PROPERTY_NAME).disable();
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
            component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME).disable();
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
            component.addProperty(new Bulletproof());
            kaboom(component);
            if (component.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) {
                DroneProperty droneProperty = component.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
                droneProperty.setHitPoints(droneProperty.getHitPoints() - 1);
            }
            executorService.schedule(() -> destroy(component), 1, TimeUnit.SECONDS);
        } else if (component == TestSceneLoader.MAIN_GOAT) {
            GameComponent component1 = new GameSceneComponent((GameComponent) component);
            kaboom(component1);
            executorService.schedule(() -> destroy(component1), 1, TimeUnit.SECONDS);
            resetComponent(component);
        }
    }

    private void resetComponent(Component component) {
        TransformProperty transform = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        float x = 10 + random.nextFloat() * 200 - 100f;
        float y = random.nextFloat() * 200 - 100f;
        float z = random.nextFloat() * 200 - 100f;
        transform.setTranslation(new Vector3f(x, y, z));
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
            materialProperty.getMaterial().setTransparency(1.0f);
            bulletproofProperty.disable();
        }, 2, TimeUnit.SECONDS);
    }


    private void kaboom(Component component) {
        ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0), 0, 0);
        ParticleFactory<TexturedParticle> factory = new RandomSpreadingTexturedParticleFactory(0.04f, 300, true, true);
        TexturedParticleSystem system = new TexturedParticleSystem(animator, factory, 1000, explosionSpritesheet);
        component.addProperty(new GraphicsParticleEmitterProperty(system));
        executorService.schedule(() -> system.setEmit(false), 200, TimeUnit.MILLISECONDS);
    }

    private void destroy(Component componentHit) {
        componentHit.destroy();
        getOwner().destroy();
    }

    //TODO REMOVE AS SOON AS DISABLING COLLIDER WORKS
    private static class Bulletproof extends Property {

        public Bulletproof() {
            super();
        }
    }

}
