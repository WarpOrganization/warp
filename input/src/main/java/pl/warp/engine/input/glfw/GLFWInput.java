package pl.warp.engine.input.glfw;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.event.Event;
import pl.warp.engine.input.*;
import pl.warp.engine.input.event.KeyPressedEvent;
import pl.warp.engine.input.event.KeyReleasedEvent;
import pl.warp.engine.input.event.MouseButtonPressedEvent;
import pl.warp.engine.input.event.MouseButtonReleasedEvent;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 16
 */

@SuppressWarnings("Duplicates") //srsly intellij?
public class GLFWInput implements Input {

    private Scene scene;

    public GLFWInput(Scene scene) {
        this.scene = scene;
    }

    private long windowHandle;

    private boolean[] keyboardKeys = new boolean[2048];
    private boolean[] mouseButtons = new boolean[8];

    private double lastScrollPos;
    private double scrollPos;
    private double scrollPosDelta;

    private Vector2f cursorPosition;
    private Vector2f cursorPositionDelta = new Vector2f(0, 0);

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
        lastScrollPos = y;
    }


    private void triggerEvent(Event event) {
        scene.triggerEvent(event);
    }

    @Override
    public void update() {
        updateScroll();
        updateMousePos();
    }

    private void updateScroll() {
        scrollPosDelta = scrollPos - lastScrollPos;
        scrollPos = lastScrollPos;
    }

    private void updateMousePos() {
        Vector2f currentCursorPos = getRealCursorPos();
        currentCursorPos.sub(cursorPosition, this.cursorPositionDelta);
        this.cursorPosition = currentCursorPos;
    }

    @Override
    public Vector2f getCursorPosition() {
        return cursorPosition;
    }

    @Override
    public Vector2f getCursorPositionDelta() {
        return cursorPositionDelta;
    }

    @Override
    public double getScrollDelta() {
        return scrollPosDelta;
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
