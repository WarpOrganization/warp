package net.warpgame.engine.net.messagetypes.event;

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

    public int getSourcePeerId() {
        return sourceId;
    }

    public int getTargetPeerId() {
        return targetId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    void setTargetId(int targetId){
        this.targetId = targetId;
    }

    public boolean isTransfered() {
        return transfered;
    }

    public void setTransfered(boolean transfered) {
        this.transfered = transfered;
    }
}
