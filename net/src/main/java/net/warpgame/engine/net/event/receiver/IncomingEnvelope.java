package net.warpgame.engine.net.event.receiver;

/**
 * @author Hubertus
 * Created 02.01.2018
 */
public class IncomingEnvelope {
    private Object deserializedEvent;
    private int targetComponentId;
    private int dependencyId;
    private int eventType;
    private long timestamp;

    IncomingEnvelope(Object deserializedEvent, int targetComponentId, int dependencyId, int eventType, long timestamp) {
        this.deserializedEvent = deserializedEvent;
        this.targetComponentId = targetComponentId;
        this.dependencyId = dependencyId;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    public int getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(int dependencyId) {
        this.dependencyId = dependencyId;
    }

    public Object getDeserializedEvent() {
        return deserializedEvent;
    }

    public void setDeserializedEvent(Object deserializedEvent) {
        this.deserializedEvent = deserializedEvent;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTargetComponentId() {
        return targetComponentId;
    }

    public void setTargetComponentId(int targetComponentId) {
        this.targetComponentId = targetComponentId;
    }
}
