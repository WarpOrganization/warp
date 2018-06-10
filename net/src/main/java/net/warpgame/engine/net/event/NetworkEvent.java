package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public class NetworkEvent extends Event {
    private int targetId;
    private int sourceId;
    private boolean transfered = false;

    /**
     * Event will be sent to server by default
     */
    public NetworkEvent() {
        targetId = 0;
    }

    /**
     * Event will be sent to specified client
     *
     * @param targetId Id of receiving client
     */
    public NetworkEvent(int targetId) {
        this.targetId = targetId;
    }

    public int getSourceClientId() {
        return sourceId;
    }

    public int getTargetClientId() {
        return targetId;
    }

    void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isTransfered() {
        return transfered;
    }

    public void setTransfered(boolean transfered) {
        this.transfered = transfered;
    }
}
