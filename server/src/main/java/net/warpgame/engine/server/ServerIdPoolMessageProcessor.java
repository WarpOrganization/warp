package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.IdPool;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.IdPoolMessageSource;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessage;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessageProcessor;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolRequest;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolResponse;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
@Service
@Profile("server")
public class ServerIdPoolMessageProcessor extends IdPoolMessageProcessor {

    private Serializers serializers;
    private ServerPublicIdPoolProvider idPoolProvider;
    private IdPoolMessageSource messageSource;

    public ServerIdPoolMessageProcessor(Serializers serializers,
                                        ServerPublicIdPoolProvider idPoolProvider,
                                        IdPoolMessageSource messageSource) {
        this.serializers = serializers;
        this.idPoolProvider = idPoolProvider;
        this.messageSource = messageSource;
    }

    @Override
    public void processMessage(Peer sourcePeer, SerializationBuffer messageContent) {
        IdPoolMessage message = (IdPoolMessage) serializers.deserialize(messageContent);
        if (message instanceof IdPoolRequest) {
            IdPool pool = idPoolProvider.issueIdPool((Client) sourcePeer);
            messageSource.pushMessage(new IdPoolResponse(pool.getOffset(), sourcePeer.getId()));
        }
    }
}
