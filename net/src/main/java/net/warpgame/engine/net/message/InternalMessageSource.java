package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessage;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class InternalMessageSource extends MessageSource<InternalMessage> {

    private final Serializers serializers;
    private SerializationBuffer buffer = new SerializationBuffer(1500);


    public InternalMessageSource(MessageQueue messageQueue,
                                 EnvelopeAddressingService envelopeAddressingService,
                                 Serializers serializers) {
        super(messageQueue, envelopeAddressingService);
        this.serializers = serializers;
    }

    @Override
    void serializeMessage(InternalMessage message, EnvelopeAddressingService addressingService) {
        buffer.setWriterIndex(0);
        serializers.serialize(buffer, message);
        addressingService.createEnvelope(buffer.getWrittenData(), message.getTargetPeerId());
    }

    @Override
    int getMessageType() {
        return 1;
    }
}
