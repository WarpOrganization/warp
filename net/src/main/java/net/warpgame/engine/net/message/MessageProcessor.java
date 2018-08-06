package net.warpgame.engine.net.message;

import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.net.Peer;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public interface MessageProcessor {

    void processMessage(Peer sourcePeer, SerializationBuffer messageContent);

    int getMessageType();
}
