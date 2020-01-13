package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.command.queue.QueueManager;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.window.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported;

/**
 * @author MarconZet
 * Created 13.04.2019
 */
@Service
@Profile("graphics")
public class InstanceManager implements CreateAndDestroy {
    private static final Logger logger = LoggerFactory.getLogger(InstanceManager.class);
    private final boolean enableValidationLayers;

    private Instance instance;
    private DebugCallback debugCallback;
    private Window window;
    private PhysicalDevice physicalDevice;
    private QueueManager queueManager;
    private Device device;
    private Allocator allocator;

    public InstanceManager(Instance instance, DebugCallback debugCallback, Window window, PhysicalDevice physicalDevice, QueueManager queueManager, Device device, Allocator allocator, Config config) {
        this.instance = instance;
        this.debugCallback = debugCallback;
        this.window = window;
        this.physicalDevice = physicalDevice;
        this.queueManager = queueManager;
        this.device = device;
        this.allocator = allocator;
        enableValidationLayers = config.getValue("graphics.vulkan.debug.enabled");

    }

    @Override
    public void create() {
        if (!glfwInit()) {
            throw new AssertionError("Failed to initialize GLFW");
        }
        if (!glfwVulkanSupported()) {
            throw new AssertionError("GLFW failed to find the Vulkan loader");
        }
        instance.create();
        if(enableValidationLayers)
            debugCallback.create();
        window.create();
        physicalDevice.create();
        queueManager.create();
        device.create();
        queueManager.setDevice(device);
        allocator.create();
    }

    @Override
    public void destroy() {
        allocator.destroy();
        device.destroy();
        window.destroy();
        if(enableValidationLayers) {
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
}
