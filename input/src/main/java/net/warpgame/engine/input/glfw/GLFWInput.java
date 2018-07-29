package net.warpgame.engine.input.glfw;

import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.input.Input;
import net.warpgame.engine.input.event.KeyPressedEvent;
import net.warpgame.engine.input.event.KeyReleasedEvent;
import net.warpgame.engine.input.event.MouseButtonPressedEvent;
import net.warpgame.engine.input.event.MouseButtonReleasedEvent;
import org.apache.log4j.Logger;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 16
 */

@SuppressWarnings("Duplicates") //srsly intellij?

@Service
public class GLFWInput implements Input {

    private static Logger logger = Logger.getLogger(GLFWInput.class);

    private SceneHolder sceneHolder;

    public GLFWInput(SceneHolder sceneHolder) {
        this.sceneHolder = sceneHolder;
    }

    private long windowHandle;

    private boolean[] keyboardKeys = new boolean[2048];
    private boolean[] mouseButtons = new boolean[8];

    private Vector2f scrollPos = new Vector2f(0f, 0f);
    private Vector2f cursorPosition = new Vector2f(0f, 0f);
    private Vector2f cursorPositionDelta = new Vector2f(0f, 0f);

    public void init(long windowHandle) {
        this.windowHandle = windowHandle;
        createCallbacks();
        initCursorPos();
    }

    private void initCursorPos() {
        this.cursorPosition = getRealCursorPos();
    }

    private Vector2f getRealCursorPos() {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        GLFW.glfwGetCursorPos(windowHandle, xPos, yPos);
        return new Vector2f((float) xPos[0], (float) yPos[0]);
    }

    private void createCallbacks() {
        GLFW.glfwSetKeyCallback(windowHandle, this::keyAction);
        GLFW.glfwSetMouseButtonCallback(windowHandle, this::mouseButtonAction);
        GLFW.glfwSetScrollCallback(windowHandle, this::scrollAction);
        GLFW.glfwSetCursorPosCallback(windowHandle, this::cursorPosAction);
//        GLFW.glfwSetWindowCloseCallback(windowHandle, (window) -> {
//            logger.error("This is bad.");
//        }); TODO: Window close callback
    }

    private void keyAction(long window, int key, int scancode, int action, int mods) {
        int keyStroke = GLFWKeyMapper.toKeyCode(key);
        if (keyStroke >= keyboardKeys.length || keyStroke == KeyEvent.VK_UNDEFINED)
            return; //key unrecognized
        switch (action) {
            case GLFW.GLFW_PRESS:
                triggerEvent(new KeyPressedEvent(keyStroke));
                keyboardKeys[keyStroke] = true;
                break;
            case GLFW.GLFW_RELEASE:
                triggerEvent(new KeyReleasedEvent(keyStroke));
                keyboardKeys[keyStroke] = false;
                break;
            case GLFW.GLFW_REPEAT:
                triggerEvent(new KeyPressedEvent(keyStroke));
                keyboardKeys[keyStroke] = true;
                break;
        }
    }

    private void mouseButtonAction(long window, int button, int action, int mods) {
        int buttonCode = GLFWKeyMapper.toButtonCode(button);
        if (button >= mouseButtons.length || buttonCode == MouseEvent.NOBUTTON)
            return; //button unrecognized
        switch (action) {
            case GLFW.GLFW_PRESS:
                triggerEvent(new MouseButtonPressedEvent(buttonCode));
                mouseButtons[buttonCode] = true;
                break;
            case GLFW.GLFW_RELEASE:
                triggerEvent(new MouseButtonReleasedEvent(buttonCode));
                mouseButtons[buttonCode] = false;
                break;
            case GLFW.GLFW_REPEAT:
                triggerEvent(new MouseButtonPressedEvent(buttonCode));
                mouseButtons[buttonCode] = true;
                break;
        }
    }

    private void scrollAction(long window, double x, double y) {
        scrollPos.x += x;
        scrollPos.y += y;
    }

    private void cursorPosAction(long window, double x, double y) {
        cursorPosition.set((float) x, (float) y);
    }

    private void triggerEvent(Event event) {
        Scene scene = sceneHolder.getScene();
        if(scene != null) {
            scene.triggerEvent(event);
        } else {
            logger.warn("Failed to trigger event");
        }
    }

    @Override
    public void update() {
//        updateScroll();
//        updateMousePos();
    }

    private void updateMousePos() {
        Vector2f currentCursorPos = getRealCursorPos();
        currentCursorPos.sub(cursorPosition, this.cursorPositionDelta);
        this.cursorPosition = currentCursorPos;
    }

    @Override
    public Vector2f getCursorPosition() {
        return new Vector2f(cursorPosition);
    }

    @Deprecated
    @Override
    public Vector2f getCursorPositionDelta() {
        return cursorPositionDelta;
    }

    @Override
    public Vector2f getScrollPosition() {
        return new Vector2f(scrollPos);
    }

    @Override
    public boolean isKeyDown(int key) {
        return keyboardKeys[key];
    }

    @Override
    public boolean isMouseButtonDown(int button) {
        return mouseButtons[button];
    }

    public void destroy() {

    }
}
