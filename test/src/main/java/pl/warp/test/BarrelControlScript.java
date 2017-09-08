package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.input.Input;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;

import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class BarrelControlScript extends GameScript {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    @OwnerProperty(name = GunProperty.GUN_PROPERTY_NAME)
    private GunProperty gunProperty;

    @OwnerProperty(name = BarrelControlProperty.BARREL_CONTROL_PROPERTY)
    private BarrelControlProperty barrelControlProperty;

    public BarrelControlScript(GameComponent owner) {
        super(owner);
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
        transformProperty.rotateX(input.getCursorPositionDelta().y * barrelControlProperty.getElevationSpeed());
        if(transformProperty.getRotation().x > barrelControlProperty.getElevationMIN()/2)
            transformProperty.getRotation().setAngleAxis(barrelControlProperty.getElevationMIN(), 1f,0f,0f);
        else if(transformProperty.getRotation().x < barrelControlProperty.getElevationMAX()/2)
            transformProperty.getRotation().setAngleAxis(barrelControlProperty.getElevationMAX(), 1f,0f,0f);

        if(input.isKeyDown(KeyEvent.VK_CONTROL)) gunProperty.setTriggered(true);
        else gunProperty.setTriggered(false);
    }

    private Vector3f forwardVector = new Vector3f();

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
