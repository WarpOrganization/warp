package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.OwnerProperty;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.input.Input;

import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class BarrelControlScript extends Script {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    @OwnerProperty(name = GunProperty.GUN_PROPERTY_NAME)
    private GunProperty gunProperty;

    @OwnerProperty(name = BarrelControlProperty.BARREL_CONTROL_PROPERTY)
    private BarrelControlProperty barrelControlProperty;

    public BarrelControlScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        updateDirections();
        move();
    }

    private void move() {
        Input input = ((GameContext)getContext()).getInput();
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
