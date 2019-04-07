package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.core.Instance;
import net.warpgame.engine.graphics.core.PhysicalDevice;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.vma.VmaAllocatorCreateInfo;
import org.lwjgl.util.vma.VmaVulkanFunctions;

import static net.warpgame.engine.graphics.utility.VKUtil.translateVulkanResult;
import static org.lwjgl.util.vma.Vma.vmaCreateAllocator;
import static org.lwjgl.util.vma.Vma.vmaDestroyAllocator;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

/**
 * @author MarconZet
 * Created 07.04.2019
 */

@Service
public class Allocator extends CreateAndDestroy {
    private long allocator;

    private Instance instance;
    private PhysicalDevice physicalDevice;
    private Device device;

    public Allocator(Instance instance, PhysicalDevice physicalDevice, Device device) {
        this.instance = instance;
        this.physicalDevice = physicalDevice;
        this.device = device;
    }

    @Override
    public void create() {

        VmaVulkanFunctions functions = VmaVulkanFunctions.create();
        functions.set(instance.get(), device.get());
        VmaAllocatorCreateInfo allocatorInfo = VmaAllocatorCreateInfo.create()
                .device(device.get())
                .physicalDevice(physicalDevice.get())
                .pVulkanFunctions(functions);

        PointerBuffer pAllocator = BufferUtils.createPointerBuffer(1);
        int err = vmaCreateAllocator(allocatorInfo, pAllocator);
        if(err != VK_SUCCESS){
            throw new AssertionError("Failed to create allocator: " + translateVulkanResult(err));
        }
        allocator = pAllocator.get(0);
    }

    @Override
    public void destroy() {
        vmaDestroyAllocator(allocator);
    }

}
