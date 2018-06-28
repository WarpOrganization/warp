package net.warpgame.test;

import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.property.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.ContextService;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.input.Input;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static java.awt.event.KeyEvent.*;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class SimpleControlScript extends Script {


    private static final float CAMERA_SPEED = 0.025f;
    private static final float ROTATION_SPEED = 0.0001f;
    private static final float CAMERA_SPEED_MULTIPLIER = 3f;
    private static final float ROTATION_SPEED_MULTIPLIER = 7f;

    private float cameraSpeed = CAMERA_SPEED;

    public SimpleControlScript(Component owner) {
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
        rotate(delta);
        move(delta);
        if (input.isKeyDown(VK_ESCAPE)) {
            context.findAll(EngineThread.class).forEach(EngineThread::interrupt);
            try {
                Thread.sleep(60*delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private void move(int delta) {
        if (input.isKeyDown(VK_SHIFT))
            cameraSpeed = CAMERA_SPEED * CAMERA_SPEED_MULTIPLIER;
        else cameraSpeed = CAMERA_SPEED;

        movementVector.zero();
        if (input.isKeyDown(VK_W)) {
            movementVector.add(0, 0, -1);
//            getOwner().triggerEvent(new Envelope(new InputEvent(VK_W)));
        }
        if (input.isKeyDown(VK_S))
            movementVector.add(0, 0, 1);
        if (input.isKeyDown(VK_A))
            movementVector.add(-1, 0, 0);
        if (input.isKeyDown(VK_D))
            movementVector.add(1, 0, 0);
        if(input.isKeyDown(VK_SPACE))
            movementVector.add(0,1,0);
        if(input.isKeyDown(VK_CONTROL))
            movementVector.add(0,-1,0);

        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner(), new Quaternionf());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(cameraSpeed * delta);
            transformProperty.move(movementVector);
        }
    }


    private void rotate(int delta) {
        Vector2f cursorPositionDelta = input.getCursorPositionDelta();

        transformProperty.rotateX(-cursorPositionDelta.y * ROTATION_SPEED * delta);
        transformProperty.rotateY(-cursorPositionDelta.x * ROTATION_SPEED * delta);

        float multiplier = input.isKeyDown(VK_SHIFT) ? ROTATION_SPEED_MULTIPLIER : 1;

        if (input.isKeyDown(VK_Q))
            transformProperty.rotateZ(ROTATION_SPEED * multiplier * delta);
        if (input.isKeyDown(VK_E))
            transformProperty.rotateZ(-ROTATION_SPEED * multiplier * delta);
    }
}
