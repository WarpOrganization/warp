package pl.warp.test;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Event;
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
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.EventHandler;
import pl.warp.game.script.GameScript;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jaca777
 *         Created 2017-02-12 at 21
 */
public class KabooomScript extends GameScript<GameComponent> {

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(40);

    private GameComponent planet;
    private float planetRadius;

    public KabooomScript(GameComponent owner, GameComponent planet, float planetRadius) {
        super(owner);
        this.planet = planet;
        this.planetRadius = planetRadius;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {
        if (!getOwner().hasEnabledProperty(KaboomedProperty.KABOOMED_PROPERTY_NAME) && (isTooFar() || isInsidePlanet()))
            getOwner().triggerEvent(new KabooomEvent());
    }

    @EventHandler(eventName = KabooomEvent.KABOOM_EVENT_NAME)
    private void onKaboom(KabooomEvent event) {
        if (!getOwner().hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) return;
        getOwner().addProperty(new KaboomedProperty());
        getOwner().getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).disable();
        getOwner().getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).disable();
        getOwner().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME).disable();
        getOwner().forEachChildren(c -> {
            if (c.hasProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME))
                c.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME).disable();
        });
        getOwner().addProperty(new BulletScript.Bulletproof());
        kaboom(getOwner());
        executorService.schedule(() -> resetComponent(getOwner()), 2, TimeUnit.SECONDS);
    }

    public void resetComponent(Component component) {
        getOwner().getProperty(KaboomedProperty.KABOOMED_PROPERTY_NAME).disable();
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
            bodyProperty.setVelocity(new Vector3f(0, 0, -40.0f));
            bodyProperty.setAngularVelocity(new Vector3f(0));
            transform.getRotation().set(0, 0, 0, 1);
        }, 50, TimeUnit.MILLISECONDS);
        if (!component.hasProperty(BulletScript.Bulletproof.BULLETPROOF_PROPERTY_NAME))
            component.addProperty(new BulletScript.Bulletproof());
        Property bulletproofProperty = component.getProperty(BulletScript.Bulletproof.class);
        bulletproofProperty.enable();
        GraphicsMaterialProperty materialProperty = component.getProperty(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME);
        materialProperty.getMaterial().setTransparency(0.5f);
        executorService.schedule(() -> {
            component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME).enable();
            materialProperty.getMaterial().setTransparency(1.0f);
            bulletproofProperty.disable();
        }, 5, TimeUnit.SECONDS);
    }


    private void kaboom(GameComponent component) {
        ParticleAnimator animator1 = new SimpleParticleAnimator(new Vector3f(0), 0, 0);
        ParticleStage[] stages1 = {
                new ParticleStage(10.0f, new Vector4f(1.0f, 0.2f, 1.0f, 1.0f)),
                new ParticleStage(10.0f, new Vector4f(0.5f, 0.2f, 1.0f, 0.0f))
        };
        ParticleFactory<DotParticle> factory1 = new RandomSpreadingStageDotParticleFactory(new Vector3f(0), new Vector3f(.04f), 500, 100, true, true, stages1);
        DotParticleSystem system1 = new DotParticleSystem(animator1, factory1, 400);
        ParticleEmitterProperty property = new ParticleEmitterProperty(system1);
        component.addProperty(property);
        executorService.schedule(() -> system1.setEmit(false), 300, TimeUnit.MILLISECONDS);
    }

    private Vector3f vec = new Vector3f();

    public boolean isTooFar() {
        return Transforms.getAbsolutePosition(getOwner(), vec).length() > 7000;
    }

    private Vector3f vec2 = new Vector3f();

    public boolean isInsidePlanet() {
        Vector3f ownerPos = Transforms.getAbsolutePosition(getOwner(), vec);
        Vector3f planetPos = Transforms.getAbsolutePosition(planet, vec2);
        return ownerPos.distance(planetPos) < planetRadius;
    }

    public static class KabooomEvent extends Event {
        public static final String KABOOM_EVENT_NAME = "kaboom";

        public KabooomEvent() {
            super(KABOOM_EVENT_NAME);
        }
    }

    private static class KaboomedProperty extends Property<GameComponent> {
        public static final String KABOOMED_PROPERTY_NAME = "kaboomed";

        public KaboomedProperty() {
            super(KABOOMED_PROPERTY_NAME);
        }
    }
}
