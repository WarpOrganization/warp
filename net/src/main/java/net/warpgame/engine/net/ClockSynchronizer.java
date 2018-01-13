package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 10.01.2018
 */
public class ClockSynchronizer {
    private long delta;
    private long requestTimestamp;
    private boolean waitingForResponse;

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

    public boolean isWaitingForResponse() {
        return waitingForResponse;
    }

    public void setWaitingForResponse(boolean waitingForResponse) {
        this.waitingForResponse = waitingForResponse;
    }

    public void synchronize(long responseTimestamp) {
        long rtt = System.currentTimeMillis() - requestTimestamp;
        delta = System.currentTimeMillis() - responseTimestamp + (rtt / 2);
        waitingForResponse = false;
        System.out.println(delta);
    }
}
