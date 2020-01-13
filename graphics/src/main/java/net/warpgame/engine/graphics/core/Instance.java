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
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.vulkan.EXTDebugReport.VK_EXT_DEBUG_REPORT_EXTENSION_NAME;
import static org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 05.04.2019
 */

@Service
@Profile("graphics")
public class Instance implements CreateAndDestroy {
    private static final String[] VALIDATION_LAYERS_INSTANCE_EXTENSIONS = {VK_EXT_DEBUG_REPORT_EXTENSION_NAME};
    private static final String[] INSTANCE_EXTENSIONS = {VK_KHR_SURFACE_EXTENSION_NAME};
    private final boolean enableValidationLayers ;
    private final ArrayList<String> validationLayers;

    private VkInstance instance;

    public Instance(Config config) {
        this.enableValidationLayers = config.getValue("graphics.vulkan.debug.enabled");
        this.validationLayers = config.getValue("graphics.vulkan.debug.validationLayers");
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
        PointerBuffer pInstance = BufferUtils.createPointerBuffer(1);
        int err = vkCreateInstance(pCreateInfo, null, pInstance);
        long instance = pInstance.get(0);
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
        if (enableValidationLayers) {
            PointerBuffer ppEnabledLayerNames = BufferUtils.createPointerBuffer(validationLayers.size());
            for (String layer : validationLayers) {
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
        if (enableValidationLayers) {
            size += VALIDATION_LAYERS_INSTANCE_EXTENSIONS.length;
        }

        PointerBuffer ppEnabledExtensionNames = BufferUtils.createPointerBuffer(size);
        ppEnabledExtensionNames.put(ppRequiredExtensions);
        for (String extension : INSTANCE_EXTENSIONS) {
            ppEnabledExtensionNames.put(memUTF8(extension));
        }
        if (enableValidationLayers) {
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
