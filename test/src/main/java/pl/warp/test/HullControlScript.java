package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.animation.AnimatedTextureProperty;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.OwnerProperty;

import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class HullControlScript extends GameScript {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private final float acceleration;
    private final float rotationSpeed;
    private final float maxSpeed;
    private final float brakingForce;

    @OwnerProperty(name = PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)
    private PhysicalBodyProperty bodyProperty;

    @OwnerProperty(name = GravityProperty.GRAVITY_PROPERTY_NAME)
    private GravityProperty gravityProperty;

    private GameComponent spinningWheel;
    private GameComponent tracks;

    private AnimatedTextureProperty tracksAnimation;

    private Vector3f forwardVector = new Vector3f();

    public HullControlScript(GameComponent owner, GameComponent spinningWheel, GameComponent tracks, float acceleration, float rotationSpeed, float maxSpeed, float brakingForce) {
        super(owner);
        this.spinningWheel = spinningWheel;
        this.tracks = tracks;
        this.acceleration = acceleration;
        this.rotationSpeed = rotationSpeed;
        this.maxSpeed = maxSpeed;
        this.brakingForce = brakingForce;
    }

    @Override
    protected void init() {
        this.tracksAnimation = tracks.getProperty(AnimatedTextureProperty.ANIMATED_TEXTURE_PROPERTY_NAME);
    }

    @Override
    protected void update(int delta) {
        updateDirections();
        if (gravityProperty.isStanding())
            fixVelocity();
        move(delta);
    }

    private void move(int delta) {
        Input input = getContext().getInput();
        if (input.isKeyDown(KeyEvent.VK_W))
            linearMove(-acceleration * delta);
        else if (input.isKeyDown(KeyEvent.VK_S))
            linearMove(acceleration * delta);
        else if (gravityProperty.isStanding()) brake();

        if (input.isKeyDown(KeyEvent.VK_A))
            anguarMove(0, rotationSpeed, 0);
        else if (input.isKeyDown(KeyEvent.VK_D))
            anguarMove(0, -rotationSpeed, 0);
        else anguarMove(0, 0, 0);
    }

    private Vector3f vel = new Vector3f();

    private void brake() {
        vel.set(bodyProperty.getVelocity());
        vel.negate().normalize();
        vel.mul(brakingForce);
        if (bodyProperty.getVelocity().length() > brakingForce / bodyProperty.getMass()) {
            bodyProperty.applyForce(vel);
        } else {
            bodyProperty.getVelocity().set(0);
        }
    }

    private void linearMove(float speed) {
        float direction = Math.signum(forwardVector.dot(bodyProperty.getVelocity()));
        this.tracksAnimation.setDelta(tracksAnimation.getDelta() + (bodyProperty.getVelocity().length() * 0.003f * direction));
        forwardVector.normalize().mul(speed);
        if(bodyProperty.getVelocity().length() < maxSpeed)
            bodyProperty.applyForce(forwardVector);
    }

    private Vector3f tmp = new Vector3f();

    private void fixVelocity() {
        bodyProperty.getVelocity().set(
                tmp
                .set(forwardVector)
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
