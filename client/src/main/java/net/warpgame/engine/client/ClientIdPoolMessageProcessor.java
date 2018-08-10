package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessage;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessageProcessor;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolResponse;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
@Service
@Profile("client")
public class ClientIdPoolMessageProcessor extends IdPoolMessageProcessor {

    private ClientPublicIdPoolProvider publicIdPoolProvider;
    private Serializers serializers;

    public ClientIdPoolMessageProcessor(ClientPublicIdPoolProvider publicIdPoolProvider, Serializers serializers) {
        this.publicIdPoolProvider = publicIdPoolProvider;
        this.serializers = serializers;
    }

    @Override
    public void processMessage(Peer sourcePeer, SerializationBuffer messageContent) {
        IdPoolMessage message = (IdPoolMessage) serializers.deserialize(messageContent);
        if (message instanceof IdPoolResponse) {
            IdPoolResponse response = (IdPoolResponse) message;
        }
    }
}
