package net.warpgame.servertest.client.scripts;

import net.warpgame.content.KeyboardInputEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.property.TransformProperty;
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

    private boolean forward, backward, left, right,
            rotateUp, rotateDown, rotateLeft, rotateRight, rotateLeftX, rotateRightX, AVR, CAS;

    public MultiplayerControlScript(Component owner) {
        super(owner);
    }

    @OwnerProperty(@IdOf(TransformProperty.class))
    private TransformProperty transformProperty;

    @ContextService
    private Input input;
    @ContextService
    private Context context;

    private Vector3f movementVector = new Vector3f();

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
//        rotate(delta);
        move(delta);
        if (input.isKeyDown(VK_ESCAPE)) {
            context.findAll(EngineThread.class).forEach(EngineThread::interrupt);
            try {
                Thread.sleep(60 * delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private void move(int delta) {
        if (input.isKeyDown(VK_SHIFT))
            cameraSpeed = CAMERA_SPEED * 3;
        else cameraSpeed = CAMERA_SPEED;

        if (input.isKeyDown(VK_F1))
            getOwner().triggerOnRoot(new KeyboardInputEvent(VK_F1, true));

        movementVector.zero();
        if (input.isKeyDown(VK_W) && !forward) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_W, true));
            forward = true;
        }
        if (input.isKeyDown(VK_S) && !backward) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_S, true));
            backward = true;
        }
        if (input.isKeyDown(VK_A) && !left) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_A, true));
            left = true;
        }
        if (input.isKeyDown(VK_D) && !right) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_D, true));
            right = true;
        }
        if (input.isKeyDown(VK_UP) && !rotateUp) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_UP, true));
            rotateUp = true;
        }
        if (input.isKeyDown(VK_DOWN) && !rotateDown) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_DOWN, true));
            rotateDown = true;
        }
        if (input.isKeyDown(VK_LEFT) && !rotateLeft) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_LEFT, true));
            rotateLeft = true;
        }
        if (input.isKeyDown(VK_RIGHT) && !rotateRight) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_RIGHT, true));
            rotateRight = true;
        }
        if (input.isKeyDown(VK_Q) && !rotateLeftX) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_Q, true));
            rotateLeftX = true;
        }
        if (input.isKeyDown(VK_E) && !rotateRightX) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_E, true));
            rotateRightX = true;
        }
        if (input.isKeyDown(VK_C) && !CAS) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_C, true));
            CAS = true;
        }
        if (input.isKeyDown(VK_X) && !AVR) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_X, true));
            AVR = true;
        }
        if (!input.isKeyDown(VK_W) && forward) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_W, false));
            forward = false;
        }
        if (!input.isKeyDown(VK_S) && backward) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_S, false));
            backward = false;
        }
        if (!input.isKeyDown(VK_A) && left) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_A, false));
            left = false;
        }
        if (!input.isKeyDown(VK_D) && right) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_D, false));
            right = false;
        }
        if (!input.isKeyDown(VK_UP) && rotateUp) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_UP, false));
            rotateUp = false;
        }
        if (!input.isKeyDown(VK_DOWN) && rotateDown) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_DOWN, false));
            rotateDown = false;
        }
        if (!input.isKeyDown(VK_LEFT) && rotateLeft) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_LEFT, false));
            rotateLeft = false;
        }
        if (!input.isKeyDown(VK_RIGHT) && rotateRight) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_RIGHT, false));
            rotateRight = false;
        }
        if (!input.isKeyDown(VK_Q) && rotateLeftX) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_Q, false));
            rotateLeftX = false;
        }
        if (!input.isKeyDown(VK_E) && rotateRightX) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_E, false));
            rotateRightX = false;
        }
        if (!input.isKeyDown(VK_C) && CAS) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_C, false));
            CAS = false;
        }
        if (!input.isKeyDown(VK_X) && AVR) {
            getOwner().triggerEvent(new KeyboardInputEvent(VK_X, false));
            AVR = false;
        }
    }

}
