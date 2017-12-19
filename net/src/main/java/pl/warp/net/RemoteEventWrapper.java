package pl.warp.net;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public abstract class RemoteEventWrapper {

    private byte[] eventData;
    private int targetComponentId;
    private int dependencyId;
    private long sendTime;
    private boolean confirmed;

    public RemoteEventWrapper() {
    }

    public RemoteEventWrapper(byte[] eventData, int targetComponentId, int dependencyId) {
        this.eventData = eventData;
        this.targetComponentId = targetComponentId;
        this.dependencyId = dependencyId;
    }

    public byte[] getEventData() {
        return eventData;
    }

    public void setEventData(byte[] eventData) {
        this.eventData = eventData;
    }

    public int getTargetComponentId() {
        return targetComponentId;
    }

    public void setTargetComponentId(int targetComponentId) {
        this.targetComponentId = targetComponentId;
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

    public void confirm() {
        confirmed = true;
    }
}
