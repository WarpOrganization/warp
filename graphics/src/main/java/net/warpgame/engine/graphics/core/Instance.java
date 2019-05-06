package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

import java.nio.IntBuffer;

import static net.warpgame.engine.graphics.GraphicsConfig.*;
import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 05.04.2019
 */

@Service
@Profile("graphics")
public class Instance implements CreateAndDestroy {
    private VkInstance instance;

    public Instance(Config config) {
    }

    @Override
    public void create(){
        VkApplicationInfo appInfo = VkApplicationInfo.create()
                .sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
                .pNext(NULL)
                .pApplicationName(memUTF8("Warp Demo"))
                .pEngineName(memUTF8("Warp Engine"))
                .applicationVersion(VK_MAKE_VERSION(0, 1, 0))
                .engineVersion(VK_MAKE_VERSION(0,1,0))
                .apiVersion(VK_API_VERSION_1_0);

        PointerBuffer ppEnabledExtensionNames = getInstanceExtensions();
        PointerBuffer ppEnabledLayerNames = getValidationLayers();

        VkInstanceCreateInfo pCreateInfo = VkInstanceCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                .pNext(NULL)
                .pApplicationInfo(appInfo)
                .ppEnabledExtensionNames(ppEnabledExtensionNames)
                .ppEnabledLayerNames(ppEnabledLayerNames);
        PointerBuffer pInstance = memAllocPointer(1);
        int err = vkCreateInstance(pCreateInfo, null, pInstance);
        long instance = pInstance.get(0);
        memFree(pInstance);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create VkInstance", err);
        }
        this.instance = new VkInstance(instance, pCreateInfo);
        memFree(appInfo.pApplicationName());
        memFree(appInfo.pEngineName());
    }

    @Override
    public void destroy(){
        vkDestroyInstance(instance, null);
    }

    private PointerBuffer getValidationLayers() {
        if (ENABLE_VALIDATION_LAYERS) {
            PointerBuffer ppEnabledLayerNames = BufferUtils.createPointerBuffer(VALIDATION_LAYERS.length);
            for (String layer : VALIDATION_LAYERS) {
                ppEnabledLayerNames.put(memUTF8(layer));
            }
            return ppEnabledLayerNames.flip();
        } else {
            return null;
        }
    }

    private PointerBuffer getInstanceExtensions() {
        IntBuffer pExtensionCount = BufferUtils.createIntBuffer(1);
        vkEnumerateInstanceExtensionProperties((CharSequence) null, pExtensionCount, null);
        int extensionCount = pExtensionCount.get(0);
        VkExtensionProperties.Buffer ppExtensions = VkExtensionProperties.create(extensionCount);
        vkEnumerateInstanceExtensionProperties((CharSequence) null, pExtensionCount, ppExtensions);

        //TODO check if all extensions are present
        PointerBuffer ppRequiredExtensions = glfwGetRequiredInstanceExtensions();
        if (ppRequiredExtensions == null) {
            throw new AssertionError("Failed to find list of required Vulkan extensions");
        }

        int size = ppExtensions.remaining() + INSTANCE_EXTENSIONS.length;
        if (ENABLE_VALIDATION_LAYERS) {
            size += VALIDATION_LAYERS_INSTANCE_EXTENSIONS.length;
        }

        PointerBuffer ppEnabledExtensionNames = BufferUtils.createPointerBuffer(size);
        ppEnabledExtensionNames.put(ppRequiredExtensions);
        for (String extension : INSTANCE_EXTENSIONS) {
            ppEnabledExtensionNames.put(memUTF8(extension));
        }
        if (ENABLE_VALIDATION_LAYERS) {
            for (String extension : VALIDATION_LAYERS_INSTANCE_EXTENSIONS) {
                ppEnabledExtensionNames.put(memUTF8(extension));
            }
        }
        ppEnabledExtensionNames.flip();

        return ppEnabledExtensionNames;
    }

    public VkInstance get() {
        return instance;
    }
}
