package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;

import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class BarrelControlScript extends GameScript {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private  final float elevationSpeed;
    private final float elevationMAX;
    private final float elevationMIN;

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    @OwnerProperty(name = GunProperty.GUN_PROPERTY_NAME)
    private GunProperty gunProperty;

    public BarrelControlScript(GameComponent owner, float elevationSpeed, float elevationMAX, float elevationMIN) {
        super(owner);
        this.elevationSpeed = elevationSpeed * (float)Math.PI/5000;
        this.elevationMAX = -(float)Math.toRadians(elevationMAX);
        this.elevationMIN = -(float)Math.toRadians(elevationMIN);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {
        updateDirections();
        move();
    }

    private void move() {
        Input input = getContext().getInput();
        transformProperty.rotateX(input.getCursorPositionDelta().y * elevationSpeed);
        if(transformProperty.getRotation().x > elevationMIN/2)
            transformProperty.getRotation().setAngleAxis(elevationMIN, 1f,0f,0f);
        else if(transformProperty.getRotation().x < elevationMAX/2)
            transformProperty.getRotation().setAngleAxis(elevationMAX, 1f,0f,0f);

        if(input.isKeyDown(KeyEvent.VK_CONTROL)) gunProperty.setTriggered(true);
        else gunProperty.setTriggered(false);
    }

    private Vector3f forwardVector = new Vector3f();

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
