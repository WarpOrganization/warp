package net.warpgame.engine.net.event.receiver;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.net.DesynchronizationException;
import net.warpgame.engine.net.event.StateChangeHandler;
import net.warpgame.engine.net.event.StateChangeRequestMessage;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 02.01.2018
 */
public class MessageReceiver {
    private PriorityQueue<IncomingEnvelope> eventQueue = new PriorityQueue<>(new IncomingEnvelopeComparator());
    private EventDeserializer deserializer = new EventDeserializer();
    private int minDependencyId = 1;

    private ComponentRegistry componentRegistry;
    private StateChangeHandler stateChangeHandler;

    public MessageReceiver(ComponentRegistry componentRegistry, StateChangeHandler stateChangeHandler) {
        this.componentRegistry = componentRegistry;
        this.stateChangeHandler = stateChangeHandler;
    }

    public synchronized void addFastSerializableEvent(ByteBuf eventContent, int dependencyId) {
        //TODO implement
    }

    public synchronized void addEvent(ByteBuf eventContent, int targetComponentId, int eventType, int dependencyId, long timestamp) {
        if (checkDependency(dependencyId)) {
            eventQueue.add(new IncomingEventEnvelope(
                    deserializer.deserialize(eventContent),
                    targetComponentId, dependencyId,
                    eventType,
                    timestamp));
            triggerIncomingEvents();
        }
    }

    public synchronized void addInternalMessage(ByteBuf messageContent, int dependencyId, long timestamp) {
        if (checkDependency(dependencyId)) {
            eventQueue.add(new IncomingInternalMessageEnvelope(
                    deserializer.deserialize(messageContent),
                    dependencyId,
                    timestamp
            ));
            triggerIncomingEvents();
        }
    }

    private void triggerIncomingEvents() {
        while (!eventQueue.isEmpty() && minDependencyId == eventQueue.peek().getDependencyId()) {
            IncomingEnvelope envelope = eventQueue.poll();
            if (envelope.isInternal()) {
                stateChangeHandler.handleMessage((StateChangeRequestMessage) envelope.getDeserializedEvent());
            } else {
                Component targetComponent = componentRegistry.getComponent(envelope.getTargetComponentId());
                if (targetComponent == null)
                    throw new DesynchronizationException("Event target component does not exist.");
                try {
                    targetComponent.triggerEvent((Event) envelope.getDeserializedEvent());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
