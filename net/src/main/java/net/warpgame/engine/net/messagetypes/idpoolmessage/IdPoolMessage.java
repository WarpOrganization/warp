package net.warpgame.engine.net.messagetypes.idpoolmessage;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public abstract class IdPoolMessage {
    private int targetPeerId;

    public IdPoolMessage(int targetPeerId) {
        this.targetPeerId = targetPeerId;
    }

    public int getTargetPeerId() {
        return targetPeerId;
    }

    public void setTargetPeerId(int targetPeerId) {
        this.targetPeerId = targetPeerId;
    }
}
