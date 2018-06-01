package net.warpgame.engine.net.message;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.net.Peer;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public interface MessageProcessor {

    void processMessage(Peer sourcePeer, ByteBuf messageContent);

    int getMessageType();
}
