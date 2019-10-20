package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.OneTimeCommandPool;
import net.warpgame.engine.graphics.command.Queue;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.scene.Loadable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author MarconZet
 * Created 10.05.2019
 */
@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
//TODO this task should happen in another thread and it MUST be synchronised with VulkanTask when it executes to onInit() and onClose()
public class VulkanLoadTask extends EngineTask {
    private static final Logger logger = LoggerFactory.getLogger(VulkanLoadTask.class);

    private BlockingQueue<Loadable> loadQueue = new ArrayBlockingQueue<>(10);
    private BlockingQueue<Loadable> unloadQueue = new ArrayBlockingQueue<>(10);
    private CommandPool commandPool;

    private Device device;
    private Allocator allocator;
    private Queue queue;

    public VulkanLoadTask(Device device, Allocator allocator, GraphicsQueue queue) {
        this.device = device;
        this.allocator = allocator;
        this.queue = queue;
    }

    @Override
    protected void onInit() {
        commandPool = new OneTimeCommandPool(device, queue);
    }

    @Override
    public void update(int delta) {
        Loadable loadable;
        while ((loadable = loadQueue.poll()) != null) {
            try {
                loadable.loadResource(device, allocator, commandPool);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        while ((loadable = unloadQueue.poll()) != null) {
            loadable.unloadResource();
        }
    }

    @Override
    protected void onClose() {
        //leaves everything to vulkan
    }

    public void addToLoad(Loadable loadable) {
        loadQueue.add(loadable);
    }

    public void addToUnload(Loadable loadable){
        unloadQueue.add(loadable);
    }
}
