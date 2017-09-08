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

/**
 * Created by Marcin on 04.03.2017.
 */
public class TurretControlScript extends Script {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    @OwnerProperty(name = TurretProperty.TURRET_PROPERTY_NAME)
    private TurretProperty turretProperty;

    public TurretControlScript(Component owner) {
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
        transformProperty.rotateY(-input.getCursorPositionDelta().x*turretProperty.getRotationSpeed());
    }

    private Vector3f forwardVector = new Vector3f();

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
