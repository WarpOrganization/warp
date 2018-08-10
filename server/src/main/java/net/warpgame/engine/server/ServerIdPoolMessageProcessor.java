package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessageProcessor;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
@Service
@Profile("server")
public class ServerIdPoolMessageProcessor extends IdPoolMessageProcessor {

    @Override
    public void processMessage(Peer sourcePeer, SerializationBuffer messageContent) {
        //TODO implement
    }
}
