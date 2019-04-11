package net.warpgame.engine.graphics.command;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Device;

/**
 * @author MarconZet
 * Created 07.04.2019
 */
@Service
public class GraphicsQueue extends Queue {
    private QueueFamilyIndices queueFamilyIndices;

    public GraphicsQueue(Device device, QueueFamilyIndices queueFamilyIndices) {
        super(device);
        this.queueFamilyIndices = queueFamilyIndices;
    }

    @Override
    public int getFamily() {
        return queueFamilyIndices.getGraphicsFamily();
    }
}
