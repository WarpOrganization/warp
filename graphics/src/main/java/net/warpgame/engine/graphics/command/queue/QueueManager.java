package net.warpgame.engine.graphics.command.queue;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;

import java.util.stream.Stream;

/**
 * @author MarconZet
 * Created 11.01.2020
 */
@Service
@Profile("graphics")
public class QueueManager implements CreateAndDestroy {
    private QueueFamilyProperties[] queueFamiliesProperties;

    private QueueFamilyProperties graphics;
    private QueueFamilyProperties present;
    private QueueFamilyProperties transport;

    private Queue graphicsQueue;
    private PresentationQueue presentationQueue;
    private Queue transportQueue;

    private Device device;
    private QueueFamilyIndices queueFamilyIndices;

    public QueueManager(QueueFamilyIndices queueFamilyIndices) {
        this.queueFamilyIndices = queueFamilyIndices;
    }

    @Override
    public void create() {
        this.queueFamiliesProperties = queueFamilyIndices.getQueueFamiliesProperties();
        choseQueues();
    }

    public void setDevice(Device device) {
        this.device = device;
        createQueues();
        synchronized (this) {
            created = true;
            notifyAll();
        }
    }

    private boolean created = false;

    @Override
    public boolean isCreated() {
        return created;
    }

    @Override
    public void destroy() {

    }

    private void choseQueues() {
        graphics = Stream.of(queueFamiliesProperties).filter(QueueFamilyProperties::isGraphicsSupport).findFirst().get();
        present = Stream.of(queueFamiliesProperties).filter(QueueFamilyProperties::isPresentSupport).filter(x -> x != graphics).findFirst().orElseGet(() -> graphics);
        transport = Stream.of(queueFamiliesProperties).filter(QueueFamilyProperties::isPresentSupport).filter(x -> x != graphics && x != present).findFirst().orElseGet(() -> graphics);
    }

    private void createQueues() {
        graphicsQueue = new PresentationQueue(graphics.getQueueFamilyIndex(), 0, device);
        presentationQueue = isPresentationUnique() ? new PresentationQueue(present.getQueueFamilyIndex(), 0, device) : (PresentationQueue) graphicsQueue;
        transportQueue = isTransportUnique() ? new Queue(transport.getQueueFamilyIndex(), 0, device) : graphicsQueue;
    }

    public PresentationQueue getPresentationQueue() {
        return presentationQueue;
    }

    public Queue getTransportQueue() {
        return transportQueue;
    }

    public Queue getGraphicsQueue() {
        return graphicsQueue;
    }

    public boolean isPresentationUnique() {
        return graphics != present;
    }

    public boolean isTransportUnique() {
        return graphics != transport;
    }

    public int queueNumber() {
        return 1 + (isTransportUnique() ? 1 : 0) + (isPresentationUnique() ? 1 : 0);
    }

    public int getGraphicsFamily() {
        return graphics.getQueueFamilyIndex();
    }

    public int getPresentFamily() {
        return present.getQueueFamilyIndex();
    }

    public int getTransportFamily() {
        return transport.getQueueFamilyIndex();
    }
}
