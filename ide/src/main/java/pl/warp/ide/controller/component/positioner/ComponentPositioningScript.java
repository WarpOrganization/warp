package pl.warp.ide.controller.component.positioner;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameInputHandler;
import pl.warp.game.script.GameScriptWithInput;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 12
 */
public class ComponentPositioningScript extends GameScriptWithInput<GameComponent> {

    private static final float COMPONENT_SPEED = 0.025f;
    private static final float ROTATION_SPEED = 0.003f;
    private Runnable callback;

    public ComponentPositioningScript(GameComponent owner, Runnable positionedCallback) {
        super(owner);
        this.callback = positionedCallback;
    }

    private TransformProperty componentTransform;

    @Override
    protected void init() {
        this.componentTransform = getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    @Override
    protected void update(int delta) {
        GameInputHandler inputHandler = getInputHandler();
        if (inputHandler.wasKeyPressed(KeyEvent.VK_ENTER)) callback.run();
        if (inputHandler.isKeyDown(VK_CONTROL)) rotate(delta);
        else move(delta);

    }


    private Vector3f movementVector = new Vector3f();

    private void move(int delta) {
        float componentSpeed;
        if (getInputHandler().isKeyDown(VK_SHIFT))
            componentSpeed = COMPONENT_SPEED * 50;
        else componentSpeed = COMPONENT_SPEED;

        movementVector.zero();
        if (getInputHandler().isKeyDown(VK_W))
            movementVector.add(0, 0, -1);
        if (getInputHandler().isKeyDown(VK_S))
            movementVector.add(0, 0, 1);
        if (getInputHandler().isKeyDown(VK_A))
            movementVector.add(-1, 0, 0);
        if (getInputHandler().isKeyDown(VK_D))
            movementVector.add(1, 0, 0);
        if (getInputHandler().isKeyDown(VK_R))
            movementVector.add(0, 1, 0);
        if (getInputHandler().isKeyDown(VK_F))
            movementVector.add(0, -1, 0);
        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(componentSpeed * delta);
            componentTransform.move(movementVector);
        }
    }

    private void rotate(int delta) {
        if (getInputHandler().isKeyDown(VK_W))
            componentTransform.getRotation().rotateX(ROTATION_SPEED * delta);
        if (getInputHandler().isKeyDown(VK_S))
            componentTransform.getRotation().rotateX(-ROTATION_SPEED * delta);
        if (getInputHandler().isKeyDown(VK_A))
            componentTransform.getRotation().rotateY(ROTATION_SPEED * delta);
        if (getInputHandler().isKeyDown(VK_D))
            componentTransform.getRotation().rotateY(-ROTATION_SPEED * delta);
        if (getInputHandler().isKeyDown(VK_Q))
            componentTransform.getRotation().rotateZ(ROTATION_SPEED * delta);
        if (getInputHandler().isKeyDown(VK_E))
            componentTransform.getRotation().rotateZ(-ROTATION_SPEED * delta);
    }
}
