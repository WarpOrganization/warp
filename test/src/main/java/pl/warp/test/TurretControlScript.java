package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.common.input.Input;
import pl.warp.engine.common.properties.TransformProperty;
import pl.warp.engine.common.properties.Transforms;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;

/**
 * Created by Marcin on 04.03.2017.
 */
public class TurretControlScript extends GameScript {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private final float rotationSpeed;

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    public TurretControlScript(GameComponent owner, float rotationSpeed) {
        super(owner);
        this.rotationSpeed = rotationSpeed * (float)Math.PI/5000;
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
        transformProperty.rotateY(-input.getCursorPositionDelta().x*rotationSpeed);
    }

    private Vector3f forwardVector = new Vector3f();

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
