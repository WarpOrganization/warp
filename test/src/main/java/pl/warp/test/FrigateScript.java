package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleSystem;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.particles.dot.RandomSpreadingStageDotParticleFactory;
import pl.warp.engine.physics.event.CollisionEvent;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.script.EventHandler;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.OwnerProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jaca777
 *         Created 2017-02-12 at 14
 */
public class FrigateScript extends GameScript<GameComponent> {
    private static final float VELOCITY = 10f;
    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);

    private Vector3f rotationCenter;

    public FrigateScript(GameComponent owner, Vector3f rotationCenter) {
        super(owner);
        this.rotationCenter = rotationCenter;
    }

    @OwnerProperty(name = PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)
    private PhysicalBodyProperty body;

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private TransformProperty transform;

    @Override
    protected void init() {
    }

    private Vector3f bulletPos = new Vector3f();
    @EventHandler(eventName = CollisionEvent.COLLISION_EVENT_NAME)
    private void onCollision(CollisionEvent event){
        Component secondComponent = event.getSecondComponent();
        if(secondComponent.hasProperty(BulletProperty.BULLET_PROPERTY_NAME)){
            Transforms.getAbsolutePosition(secondComponent, bulletPos);
            GameComponent particles = new GameSceneComponent(getOwner().getParent());
            ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0.00001f, 0.0f, 0), 0, 0);
            ParticleStage[] stages = {
                    new ParticleStage(4.0f, new Vector4f(0.2f, 0.5f, 1.0f, 2.0f)),
                    new ParticleStage(4.0f, new Vector4f(0.2f, 0.5f, 1.0f, 0.0f))
            };
            ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(new Vector3f(.002f), 800, 100, true, true, stages);
            particles.addProperty(new ParticleEmitterProperty(new DotParticleSystem(animator, factory, 300)));
            executorService.schedule(particles::destroy, 1000, TimeUnit.MILLISECONDS);
        }
    }


    private Vector3f vector3f = new Vector3f();
    private Vector3f forward = new Vector3f(-1, 0, 0);
    private Quaternionf quaternionf = new Quaternionf();

    @Override
    protected void update(int delta) {
        Vector3f absolutePosition = Transforms.getAbsolutePosition(getOwner(), vector3f);
        Vector3f toCenterVec = rotationCenter.sub(absolutePosition, absolutePosition).normalize();
        Vector3f dir = toCenterVec.cross(UP_VECTOR);
        quaternionf.identity();
        quaternionf.rotateTo(forward, dir);
        transform.getRotation().set(quaternionf);
        body.setVelocity(dir.mul(VELOCITY));
    }


}
