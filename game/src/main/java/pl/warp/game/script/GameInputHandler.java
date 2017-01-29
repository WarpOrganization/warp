package pl.warp.game.script;

import org.joml.Vector2f;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.input.*;
import pl.warp.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 14
 */
public class GameInputHandler {

    private Input input;
    private Scene scene;

    private boolean[] keyboardKeys = new boolean[349];
    private boolean[] mouseButtons = new boolean[8];


    public GameInputHandler(GameContext context) {
        this.scene = context.getScene();
        this.input = context.getInput();
        init();
    }

    private void init() {
        SimpleListener.createListener(scene, KeyPressedEvent.KEY_PRESSED_EVENT_NAME, this::onKeyPressed);
        SimpleListener.createListener(scene, MouseButtonPressedEvent.MOUSE_BUTTON_PRESSED_EVENT_NAME, this::onButtonPressed);
    }

    public boolean wasKeyPressed(int key){
        if(keyboardKeys[key]){
            keyboardKeys[key] = false;
            return true;
        } else return false;
    }

    public boolean wasMouseButtonPressed(int button) {
        if(mouseButtons[button]){
            mouseButtons[button] = false;
            return true;
        } else return false;
    }

    private void onKeyPressed(KeyPressedEvent event) {
        this.keyboardKeys[event.getKey()] = true;
    }

    private void onButtonPressed(MouseButtonPressedEvent event) {
        this.mouseButtons[event.getButton()] = true;
    }

    public Vector2f getCursorPosition() {
        return input.getCursorPosition();
    }

    public Vector2f getCursorPositionDelta() {
        return input.getCursorPositionDelta();
    }

    public boolean isKeyDown(int key) {
        return input.isKeyDown(key);
    }

    public boolean isMouseButtonDown(int button) {
        return input.isMouseButtonDown(button);
    }

    public double getScrollDelta() {
        return input.getScrollDelta();
    }
}
