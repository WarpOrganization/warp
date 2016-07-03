package pl.warp.engine.graphics;

import pl.warp.engine.graphics.input.GLFWInput;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 16
 */
public class GraphicContext {

    private int glfwWindowHandle;
    private GLFWInput input;

    public GraphicContext(int glfwWindowHandle) {
        this.glfwWindowHandle = glfwWindowHandle;
        this.input = new GLFWInput();
    }

    public GLFWInput getInput() {
        return input;
    }
}
