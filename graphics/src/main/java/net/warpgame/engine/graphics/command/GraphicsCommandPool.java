package net.warpgame.engine.graphics.command;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Device;

/**
 * @author MarconZet
 * Created 11.04.2019
 */
@Service
public class GraphicsCommandPool extends CommandPool {
    private QueueFamilyIndices queueFamilyIndices;

    public GraphicsCommandPool(Device device, QueueFamilyIndices queueFamilyIndices) {
        super(device);
        this.queueFamilyIndices = queueFamilyIndices;
    }

    @Override
    protected int getFamily() {
        return queueFamilyIndices.getGraphicsFamily();
    }
}
