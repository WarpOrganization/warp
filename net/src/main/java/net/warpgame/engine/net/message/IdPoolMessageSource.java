package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessage;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
@Service
public class IdPoolMessageSource extends MessageSource<IdPoolMessage>{

    private final Serializers serializers;
    private SerializationBuffer serializationBuffer = new SerializationBuffer(2000);

    public IdPoolMessageSource(MessageQueue messageQueue, Serializers serializers) {
        super(messageQueue);
        this.serializers = serializers;
    }


    @Override
    MessageEnvelope toAddressedEnvelope(IdPoolMessage message) {
        serializationBuffer.setWriterIndex(0);
        serializationBuffer.setReaderIndex(0);
        serializers.serialize(serializationBuffer, message);
        return new MessageEnvelope(serializationBuffer.getWrittenData(), 2);
    }
}
