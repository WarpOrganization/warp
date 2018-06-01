package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 10.01.2018
 */
public class ClockSynchronizer {
    private long delta;
    private long requestTimestamp;
    private int finishedSynchronizations = 0;
    private long minRTT = Integer.MAX_VALUE;
    private int requestId;

    public void synchronize(long responseTimestamp, int requestId) {
        if (requestId == this.requestId) {
            long rtt = System.currentTimeMillis() - requestTimestamp;
            if (rtt < minRTT) {
                delta = System.currentTimeMillis() - responseTimestamp + (rtt / 2);
                minRTT = rtt;
            }
            finishedSynchronizations++;
        }
    }

    public void startRequest(int requestId, long requestTimestamp){
        this.requestId = requestId;
        this.requestTimestamp = requestTimestamp;
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    public long getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(long requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public int getFinishedSynchronizations() {
        return finishedSynchronizations;
    }

    public void setFinishedSynchronizations(int finishedSynchronizations) {
        this.finishedSynchronizations = finishedSynchronizations;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public long getMinRTT() {
        return minRTT;
    }
}
