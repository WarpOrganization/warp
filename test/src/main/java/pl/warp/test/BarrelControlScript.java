package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.OwnerProperty;

import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class BarrelControlScript extends GameScript {
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private final float elevetionSpeed;

    @OwnerProperty(name = TransformProperty.TRANSFORM_PROPERTY_NAME)
    private  TransformProperty transformProperty;

    public BarrelControlScript(GameComponent owner, float elevetionSpeed) {
        super(owner);
        this.elevetionSpeed = elevetionSpeed * (float)Math.PI/5000;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void update(int delta) {
        updateDirections();
        move(delta);
    }

    private void move(int delta) {
        Input input = getContext().getInput();

        if (input.isKeyDown(KeyEvent.VK_UP))
            transformProperty.rotateX(elevetionSpeed *delta);
        else if (input.isKeyDown(KeyEvent.VK_DOWN))
            transformProperty.rotateX(-elevetionSpeed *delta);
    }

    private Vector3f forwardVector = new Vector3f();

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
    }
}
