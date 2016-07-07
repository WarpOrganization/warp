package pl.warp.engine.graphics.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 16
 */

@SuppressWarnings("Duplicates") //srsly intellij?
public class GLFWInput {

    private long windowHandle;

    private boolean[] keyboardKeys = new boolean[349];
    private boolean[] mouseButtons = new boolean[8];

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
    }

    private void keyAction(long window, int key, int scancode, int action, int mods) {
        if (key == -1)
            return; //key unrecognized
        switch (action) {
            case GLFW.GLFW_PRESS:
                keyboardKeys[key] = true;
                break;
            case GLFW.GLFW_RELEASE:
                keyboardKeys[key] = false;
                break;
            case GLFW.GLFW_REPEAT:
                keyboardKeys[key] = true;
                break;
        }
    }

    private void mouseButtonAction(long window, int button, int action, int mods) {
        if (button == -1)
            return; //button unrecognized
        switch (action) {
            case GLFW.GLFW_PRESS:
                mouseButtons[button] = true;
                break;
            case GLFW.GLFW_RELEASE:
                mouseButtons[button] = false;
                break;
            case GLFW.GLFW_REPEAT:
                mouseButtons[button] = true;
                break;
        }
    }

    public void update() {
        updateMousePos();
    }

    private void updateMousePos() {
        Vector2f currentCursorPos = getRealCursorPos();
        currentCursorPos.sub(cursorPosition, this.cursorPositionDelta);
        this.cursorPosition = currentCursorPos;
    }


    public Vector2f getCursorPosition() {
        return cursorPosition;
    }

    public Vector2f getCursorPositionDelta() {
        return cursorPositionDelta;
    }

    public boolean isKeyDown(int key) {
        return keyboardKeys[key];
    }

    public boolean isMouseButtonDown(int button) {
        return mouseButtons[button];
    }

    public void destroy() {

    }
}
