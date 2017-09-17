package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.annotation.OwnerProperty;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.graphics.animation.AnimatedTextureProperty;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.input.Input;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class HullControlScript extends Script {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    @OwnerProperty(name = PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)
    private PhysicalBodyProperty bodyProperty;

    @OwnerProperty(name = GravityProperty.GRAVITY_PROPERTY_NAME)
    private GravityProperty gravityProperty;

    @OwnerProperty(name = HullProperty.HULL_PROPERTY_NAME)
    private HullProperty hullProperty;

    private AnimatedTextureProperty tracksAnimation;
    private ParticleEmitterProperty tracksParticles1;
    private ParticleEmitterProperty tracksParticles2;

    private Vector3f forwardVector = new Vector3f();

    public HullControlScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        this.tracksAnimation = hullProperty.getTracks().getProperty(AnimatedTextureProperty.ANIMATED_TEXTURE_PROPERTY_NAME);
        this.tracksParticles1 = hullProperty.getTracks().getChild(0).getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
        this.tracksParticles2 = hullProperty.getTracks().getChild(1).getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
    }

    @Override
    public void onUpdate(int delta) {
        updateDirections();
        if (gravityProperty.isStanding())
            fixVelocity();
        move(delta);
    }

    private void move(int delta) {
        Input input = ((GameContext)getContext()).getInput();
        if (input.isKeyDown(KeyEvent.VK_W))
            linearMove(-hullProperty.getAcceleration() * delta);
        else if (input.isKeyDown(KeyEvent.VK_S))
            linearMove(hullProperty.getAcceleration() * delta);
        else {
            tracksParticles1.getSystem().setEmit(false);
            tracksParticles2.getSystem().setEmit(false);
            if (gravityProperty.isStanding()) brake();
        }

        if (input.isKeyDown(KeyEvent.VK_A))
            anguarMove(0, hullProperty.getRotationSpeed(), 0);
        else if (input.isKeyDown(KeyEvent.VK_D))
            anguarMove(0, -hullProperty.getRotationSpeed(), 0);
        else anguarMove(0, 0, 0);
    }

    private Vector3f vel = new Vector3f();

    private void brake() {
        vel.set(bodyProperty.getVelocity());
        vel.negate().normalize();
        vel.mul(hullProperty.getBrakingForce());
        if (bodyProperty.getVelocity().length() > hullProperty.getBrakingForce() / bodyProperty.getMass()) {
            bodyProperty.applyForce(vel);
        } else {
            bodyProperty.getVelocity().set(0);
        }
    }

    private void linearMove(float speed) {
        tracksParticles1.getSystem().setEmit(true);
        tracksParticles2.getSystem().setEmit(true);
        float direction = Math.signum(forwardVector.dot(bodyProperty.getVelocity()));
        float delta = (bodyProperty.getVelocity().length() * 0.003f * direction);
        this.tracksAnimation.setDelta(tracksAnimation.getDelta() + delta);
        rotateWheel(delta);
        forwardVector.normalize().mul(speed);
        if (bodyProperty.getVelocity().length() < hullProperty.getMaxSpeed())
            bodyProperty.applyForce(forwardVector);
    }

    private void rotateWheel(float delta) {
        TransformProperty transform = hullProperty.getSpinningWheel().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        transform.rotateX(delta * -2f);
    }

    private Vector3f tmp = new Vector3f();

    private void fixVelocity() {
        bodyProperty.getVelocity().set(
                tmp.set(forwardVector)
                        .normalize()
                        .mul(bodyProperty.getVelocity().dot(forwardVector))
        );
    }

    private Vector3f angularVelocity = new Vector3f();

    private void anguarMove(float x, float y, float z) {
        bodyProperty.setAngularVelocity(angularVelocity.set(x, y, z));
    }

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
