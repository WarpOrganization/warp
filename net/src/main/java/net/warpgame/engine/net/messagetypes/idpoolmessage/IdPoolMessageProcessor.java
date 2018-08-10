package net.warpgame.engine.net.messagetypes.idpoolmessage;

import net.warpgame.engine.net.message.MessageProcessor;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
public abstract class IdPoolMessageProcessor implements MessageProcessor {

    @Override
    public int getMessageType() {
        return 2;
    }
}
