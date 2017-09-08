package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.input.Input;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;

/**
 * Created by Marcin on 04.03.2017.
 */
public class TurretControlScript extends GameScript {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    @OwnerProperty(name = TurretProperty.TURRET_PROPERTY_NAME)
    private TurretProperty turretProperty;

    public TurretControlScript(GameComponent owner) {
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
        transformProperty.rotateY(-input.getCursorPositionDelta().x*turretProperty.getRotationSpeed());
    }

    private Vector3f forwardVector = new Vector3f();

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
