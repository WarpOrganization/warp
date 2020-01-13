package net.warpgame.engine.graphics.window;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Instance;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

import java.nio.LongBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWVulkan.glfwCreateWindowSurface;
import static org.lwjgl.vulkan.KHRSurface.vkDestroySurfaceKHR;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

/**
 * @author MarconZet
 * Created 07.04.2019
 */

@Service
@Profile("graphics")
public class Window implements CreateAndDestroy {
    private long window;
    private long surface;

    private int width;
    private int height;

    private Instance instance;

    public Window(Config config, Instance instance) {
        this.instance = instance;
        this.width = config.getValue("graphics.window.width");
        this.height = config.getValue("graphics.window.height");
    }

    @Override
    public void create() {
        createWindow();
        createSurface();
    }

    @Override
    public void destroy() {
        vkDestroySurfaceKHR(instance.get(), surface, null);
        glfwDestroyWindow(window);
    }

    public boolean shouldClose() {
        if (window == 0)
            return false;
        else
            return glfwWindowShouldClose(window);
    }

    private void createSurface() {
        LongBuffer pSurface = BufferUtils.createLongBuffer(1);
        int err = glfwCreateWindowSurface(instance.get(), window, null, pSurface);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create surface", err);
        }
        surface = pSurface.get(0);
    }

    private void createWindow() {
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "Wrap", 0, 0);
        if (window == 0) {
            throw new AssertionError("Failed to create window");
        }

        GLFWFramebufferSizeCallback framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
            }
        };
        glfwSetFramebufferSizeCallback(window, framebufferSizeCallback);
    }

    public long get() {
        return window;
    }

    public long getSurface() {
        return surface;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
