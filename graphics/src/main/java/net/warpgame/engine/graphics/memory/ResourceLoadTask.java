package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.command.poll.CommandPool;
import net.warpgame.engine.graphics.command.poll.OneTimeCommandPool;
import net.warpgame.engine.graphics.command.queue.Queue;
import net.warpgame.engine.graphics.command.queue.QueueManager;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.scene.Loadable;
import net.warpgame.engine.graphics.rendering.RecordingTask;
import net.warpgame.engine.graphics.window.SwapChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.lwjgl.vulkan.VK10.vkDeviceWaitIdle;

/**
 * @author MarconZet
 * Created 10.05.2019
 */
@Service
@Profile("graphics")
@RegisterTask(thread = "loading")
public class ResourceLoadTask extends EngineTask {
    private static final Logger logger = LoggerFactory.getLogger(ResourceLoadTask.class);

    private BlockingQueue<Loadable> loadQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<Loadable> unloadQueue = new LinkedBlockingQueue<>();
    private CommandPool commandPool;
    private Queue queue;

    private RecordingTask recordingTask;

    private SwapChain swapChain;
    private Allocator allocator;
    private QueueManager queueManager;
    private Device device;


    public ResourceLoadTask(RecordingTask recordingTask, Device device, Allocator allocator, SwapChain swapChain, QueueManager queueManager) {
        this.recordingTask = recordingTask;
        this.device = device;
        this.allocator = allocator;
        this.swapChain = swapChain;
        this.queueManager = queueManager;
    }

    @Override
    protected void onInit() {
        try {
            synchronized (swapChain) {
                if (!swapChain.isCreated())
                    swapChain.wait();
            }
        } catch (InterruptedException e) {
            if (!swapChain.isCreated())
                throw new RuntimeException("Required resources are not ready", e);
        }
        queue = queueManager.getTransportQueue();
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
            recordingTask.setRecreate(true);
        }
        while ((loadable = unloadQueue.poll()) != null) {
            loadable.unloadResource();
        }
    }

    @Override
    protected void onClose() {
        vkDeviceWaitIdle(device.get());
        //TODO proper closing
    }

    public void addToLoad(Loadable loadable) {
        loadQueue.add(loadable);
    }

    public void addToUnload(Loadable loadable) {
        unloadQueue.add(loadable);
    }
}
