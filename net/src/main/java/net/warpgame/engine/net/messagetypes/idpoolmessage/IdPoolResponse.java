package net.warpgame.engine.net.messagetypes.idpoolmessage;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public class IdPoolResponse extends IdPoolMessage {
    private int poolOffset;

    public IdPoolResponse(int poolOffset, int targetPeer) {
        super(targetPeer);
        this.poolOffset = poolOffset;
    }

    public int getPoolOffset() {
        return poolOffset;
    }

    public void setPoolOffset(int poolOffset) {
        this.poolOffset = poolOffset;
    }
}
