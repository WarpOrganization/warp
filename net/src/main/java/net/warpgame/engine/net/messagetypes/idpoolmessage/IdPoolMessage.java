package net.warpgame.engine.net.messagetypes.idpoolmessage;

import net.warpgame.engine.net.Peer;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public abstract class IdPoolMessage {
    private Peer targetPeer;

    public IdPoolMessage(Peer targetPeer) {
        this.targetPeer = targetPeer;
    }

    public Peer getTargetPeer() {
        return targetPeer;
    }

    public void setTargetPeer(Peer targetPeer) {
        this.targetPeer = targetPeer;
    }
}
