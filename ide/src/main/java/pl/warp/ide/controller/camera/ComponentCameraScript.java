package pl.warp.ide.controller.camera;

import org.joml.Vector2f;

import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameInputHandler;
import pl.warp.engine.game.script.GameScriptWithInput;

import java.awt.event.MouseEvent;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 12
 */
public class ComponentCameraScript extends GameScriptWithInput<GameComponent> {

    private GameComponent rotatingComponent;
    private Camera camera;

    private static final float ROT_SPEED = 0.06f;
    private static final float SCROLL_SPEED = 0.01f;

    public ComponentCameraScript(GameComponent cameraComponent, Camera camera, GameComponent rotatingComponent) {
        super(cameraComponent);
        this.camera = camera;
        this.rotatingComponent = rotatingComponent;
    }

    private TransformProperty transform;
    private TransformProperty rotatingTransform;

    @Override
    protected void init() {
        this.transform = getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        this.rotatingTransform = rotatingComponent.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    @Override
    protected void update(int delta) {
        rotate(delta);
        move(delta);
    }

    private void rotate(int delta) {
        GameInputHandler inputHandler = getInputHandler();
        if (inputHandler.isMouseButtonDown(MouseEvent.BUTTON1)) {
            Vector2f cursorPositionDelta = inputHandler.getCursorPositionDelta();
            rotatingTransform.rotateX(-cursorPositionDelta.y * ROT_SPEED * delta);
            rotatingTransform.rotateY(cursorPositionDelta.x * ROT_SPEED * delta);
        }
    }

    private void move(int delta) {
        if (transform.getTranslation().z > 5.0f || getInputHandler().getScrollDelta() < 0)
            transform.getTranslation().add(0, 0, -(delta * (float) getInputHandler().getScrollDelta() * SCROLL_SPEED));
    }
}
