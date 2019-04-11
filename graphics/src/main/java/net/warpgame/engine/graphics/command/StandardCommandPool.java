package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;

/**
 * @author MarconZet
 * Created 11.04.2019
 */

public class StandardCommandPool extends CommandPool {
    public StandardCommandPool(Device device, Queue queue) {
        super(device, queue);
    }

    @Override
    protected int getFlags() {
        return 0;
    }
}
