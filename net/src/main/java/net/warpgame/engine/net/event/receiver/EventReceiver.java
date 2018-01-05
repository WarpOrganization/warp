package net.warpgame.engine.net.event.receiver;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.net.DesynchronizationException;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 02.01.2018
 */

public class EventReceiver {
    private PriorityQueue<IncomingEnvelope> eventQueue = new PriorityQueue<>(new IncomingEnvelopeComparator());
    private EventDeserializer deserializer = new EventDeserializer();
    private int minDependencyId = 1;

    private ComponentRegistry componentRegistry;

    public EventReceiver(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    public synchronized void addFastSerializableEvent(ByteBuf eventContent, int dependencyId) {
        //TODO implement
    }

    public synchronized void addEvent(ByteBuf eventContent, int targetComponentId, int eventType, int dependencyId, long timestamp) {
        if (checkDependency(dependencyId)) {
            eventQueue.add(new IncomingEnvelope(
                    deserializer.deserialize(eventContent),
                    targetComponentId, dependencyId,
                    eventType,
                    timestamp));
            triggerIncomingEvents();
        }
    }

    private void triggerIncomingEvents() {
        while (!eventQueue.isEmpty() && minDependencyId == eventQueue.peek().getDependencyId()) {
            IncomingEnvelope envelope = eventQueue.peek();
            Component targetComponent = componentRegistry.getCompoenent(envelope.getTargetComponentId());
            if (targetComponent == null) throw new DesynchronizationException("Event target component does not exist.");

            targetComponent.triggerEvent((Event) envelope.getDeserializedEvent());
            minDependencyId++;
        }
    }

    private boolean checkDependency(int dependencyId) {
        if (minDependencyId > dependencyId) return false;
        if (eventQueue.isEmpty()) return true;

        Iterator<IncomingEnvelope> iterator = eventQueue.iterator();

        int d = iterator.next().getDependencyId();
        while (iterator.hasNext() && d < dependencyId) {
            d = iterator.next().getDependencyId();
            if (d == dependencyId) return false;
        }
        return true;
    }
}
