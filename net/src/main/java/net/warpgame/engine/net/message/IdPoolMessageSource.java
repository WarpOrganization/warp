package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolMessage;

/**
 * @author Hubertus
 * Created 11.08.2018
 */
@Service
@Profile("net")
public class IdPoolMessageSource extends MessageSource<IdPoolMessage> {

    private Serializers serializers;
    private SerializationBuffer serializationBuffer = new SerializationBuffer(2000);

    public IdPoolMessageSource(MessageQueue messageQueue,
                               Serializers serializers,
                               EnvelopeAddressingService addressingService) {
        super(messageQueue, addressingService);
        this.serializers = serializers;
    }


    @Override
    void serializeMessage(IdPoolMessage message, EnvelopeAddressingService envelopeAddressingService) {
        serializationBuffer.setWriterIndex(0);
        serializationBuffer.setReaderIndex(0);
        serializers.serialize(serializationBuffer, message);
        envelopeAddressingService.createEnvelope(serializationBuffer.getWrittenData(), message.getTargetPeerId());
    }

    @Override
    int getMessageType() {
        return 2;
    }
}
