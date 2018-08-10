package net.warpgame.engine.net.messagetypes.idpoolmessage;

import net.warpgame.engine.net.Peer;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public class IdPoolRequest extends IdPoolMessage {
    public IdPoolRequest(Peer targetPeer) {
        super(targetPeer);
    }
}
