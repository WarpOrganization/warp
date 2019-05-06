package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.PresentationQueue;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.window.Window;

import static net.warpgame.engine.graphics.GraphicsConfig.ENABLE_VALIDATION_LAYERS;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported;

/**
 * @author MarconZet
 * Created 13.04.2019
 */
@Service
@Profile("graphics")
public class InstanceManager implements CreateAndDestroy {
    private Instance instance;
    private DebugCallback debugCallback;
    private Window window;
    private PhysicalDevice physicalDevice;
    private Device device;
    private Allocator allocator;
    private GraphicsQueue graphicsQueue;
    private PresentationQueue presentationQueue;

    public InstanceManager(Instance instance, DebugCallback debugCallback, Window window, PhysicalDevice physicalDevice, Device device, Allocator allocator, GraphicsQueue graphicsQueue, PresentationQueue presentationQueue) {
        this.instance = instance;
        this.debugCallback = debugCallback;
        this.window = window;
        this.physicalDevice = physicalDevice;
        this.device = device;
        this.allocator = allocator;
        this.graphicsQueue = graphicsQueue;
        this.presentationQueue = presentationQueue;
    }

    @Override
    public void create() {
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }
        if (!glfwVulkanSupported()) {
            throw new AssertionError("GLFW failed to find the Vulkan loader");
        }
        instance.create();
        if(ENABLE_VALIDATION_LAYERS)
            debugCallback.create();
        window.create();
        physicalDevice.create();
        device.create();
        allocator.create();
        presentationQueue.create();
        graphicsQueue.create();
    }

    @Override
    public void destroy() {
        allocator.destroy();
        device.destroy();
        window.destroy();
        if(ENABLE_VALIDATION_LAYERS) {
            debugCallback.destroy();
        }
        instance.destroy();
    }

    public Instance getInstance() {
        return instance;
    }

    public DebugCallback getDebugCallback() {
        return debugCallback;
    }

    public Window getWindow() {
        return window;
    }

    public PhysicalDevice getPhysicalDevice() {
        return physicalDevice;
    }

    public Device getDevice() {
        return device;
    }

    public Allocator getAllocator() {
        return allocator;
    }

    public GraphicsQueue getGraphicsQueue() {
        return graphicsQueue;
    }

    public PresentationQueue getPresentationQueue() {
        return presentationQueue;
    }
}
