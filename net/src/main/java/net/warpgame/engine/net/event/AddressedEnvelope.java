package net.warpgame.engine.net.event;

import net.warpgame.engine.core.component.Component;

/**
 * @author Hubertus
 * Created 28.12.2017
 */
public class AddressedEnvelope {
    private byte[] serializedEventData;
    private int dependencyId;
    private long sendTime;
    private boolean confirmed;
    private int eventType;
    private Component targetComponent;
    private boolean shouldConfirm;
    private EventConfirmationEvent bouncerEvent;

    public byte[] getSerializedEventData() {
        return serializedEventData;
    }

    public void setSerializedEventData(byte[] serializedEventData) {
        this.serializedEventData = serializedEventData;
    }

    public int getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(int dependencyId) {
        this.dependencyId = dependencyId;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Component getTargetComponent() {
        return targetComponent;
    }

    public void setTargetComponent(Component targetComponent) {
        this.targetComponent = targetComponent;
    }

    public boolean isShouldConfirm() {
        return shouldConfirm;
    }

    public void setShouldConfirm(boolean shouldConfirm) {
        this.shouldConfirm = shouldConfirm;
    }

    public EventConfirmationEvent getBouncerEvent() {
        return bouncerEvent;
    }

    public void setBouncerEvent(EventConfirmationEvent bouncerEvent) {
        this.bouncerEvent = bouncerEvent;
    }
}
