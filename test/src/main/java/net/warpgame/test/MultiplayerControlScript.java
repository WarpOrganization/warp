package net.warpgame.test;

import net.warpgame.content.InputEvent;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.ContextService;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.input.Input;
import org.joml.Vector3f;

import static java.awt.event.KeyEvent.*;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class MultiplayerControlScript extends Script {


    private static final float CAMERA_SPEED = 0.025f;
    private static final float ROT_SPEED = 0.0001f;

    private float cameraSpeed = CAMERA_SPEED;

    private boolean forward, backward, left, right, rotateUp, rotateDown, rotateLeft, rotateRight;

    public MultiplayerControlScript(Component owner) {
        super(owner);
    }

    @OwnerProperty(@IdOf(TransformProperty.class))
    private TransformProperty transformProperty;

    @ContextService
    private Input input;


    private Vector3f movementVector = new Vector3f();

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
//        rotate(delta);
        move(delta);
    }

    private void move(int delta) {
        if (input.isKeyDown(VK_SHIFT))
            cameraSpeed = CAMERA_SPEED * 3;
        else cameraSpeed = CAMERA_SPEED;

        movementVector.zero();
        if (input.isKeyDown(VK_W) && !forward) {
            getOwner().triggerEvent(new InputEvent(VK_W, true));
            forward = true;
        }
        if (input.isKeyDown(VK_S) && !backward) {
            getOwner().triggerEvent(new InputEvent(VK_S, true));
            backward = true;
        }
        if (input.isKeyDown(VK_A) && !left) {
            getOwner().triggerEvent(new InputEvent(VK_A, true));
            left = true;
        }
        if (input.isKeyDown(VK_D) && !right) {
            getOwner().triggerEvent(new InputEvent(VK_D, true));
            right = true;
        }
        if (input.isKeyDown(VK_UP) && !rotateUp) {
            getOwner().triggerEvent(new InputEvent(VK_UP, true));
            rotateUp = true;
        }
        if (input.isKeyDown(VK_DOWN) && !rotateDown) {
            getOwner().triggerEvent(new InputEvent(VK_DOWN, true));
            rotateDown = true;
        }
        if (input.isKeyDown(VK_LEFT) && !rotateLeft) {
            getOwner().triggerEvent(new InputEvent(VK_LEFT, true));
            rotateLeft = true;
        }
        if (input.isKeyDown(VK_RIGHT) && !rotateRight) {
            getOwner().triggerEvent(new InputEvent(VK_RIGHT, true));
            rotateRight = true;
        }
        if (!input.isKeyDown(VK_W) && forward) {
            getOwner().triggerEvent(new InputEvent(VK_W, false));
            forward = false;
        }
        if (!input.isKeyDown(VK_S) && backward) {
            getOwner().triggerEvent(new InputEvent(VK_S, false));
            backward = false;
        }
        if (!input.isKeyDown(VK_A) && left) {
            getOwner().triggerEvent(new InputEvent(VK_A, false));
            left = false;
        }
        if (!input.isKeyDown(VK_D) && right) {
            getOwner().triggerEvent(new InputEvent(VK_D, false));
            right = false;
        }
        if (!input.isKeyDown(VK_UP) && rotateUp) {
            getOwner().triggerEvent(new InputEvent(VK_UP, false));
            rotateUp = false;
        }
        if (!input.isKeyDown(VK_DOWN) && rotateDown) {
            getOwner().triggerEvent(new InputEvent(VK_DOWN, false));
            rotateDown = false;
        }
        if (!input.isKeyDown(VK_LEFT) && rotateLeft) {
            getOwner().triggerEvent(new InputEvent(VK_LEFT, false));
            rotateLeft = false;
        }
        if (!input.isKeyDown(VK_RIGHT) && rotateRight) {
            getOwner().triggerEvent(new InputEvent(VK_RIGHT, false));
            rotateRight = false;
        }
    }


//    private void rotate(int delta) {
//        Vector2f cursorPositionDelta = input.getCursorPositionDelta();
//        transformProperty.rotateX(-cursorPositionDelta.y * ROT_SPEED * delta);
//        transformProperty.rotateY(-cursorPositionDelta.x * ROT_SPEED * delta);
//        if (input.isKeyDown(VK_Q))
//            transformProperty.rotateZ(ROT_SPEED * delta);
//        if (input.isKeyDown(VK_E))
//            transformProperty.rotateZ(-ROT_SPEED * delta);
//    }
}
