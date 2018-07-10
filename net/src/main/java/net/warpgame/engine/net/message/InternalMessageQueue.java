package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import net.warpgame.engine.net.internalmessage.InternalMessageSerializer;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class InternalMessageQueue extends MessageSource<InternalMessage> {

    private final EnvelopeAddressingService envelopeAddressingService;
    private final Serializers serializers;
    private InternalMessageSerializer internalMessageSerializer;
    private SerializationBuffer buffer = new SerializationBuffer(1500);


    public InternalMessageQueue(MessageQueue messageQueue,
                                EnvelopeAddressingService envelopeAddressingService,
                                Serializers serializers) {
        super(messageQueue);
        this.envelopeAddressingService = envelopeAddressingService;
        this.serializers = serializers;
        internalMessageSerializer = new InternalMessageSerializer();
    }

    @Override
    MessageEnvelope toAddressedEnvelope(InternalMessage message) {
        buffer.setWriterIndex(0);
        serializers.serialize(buffer, message);
        MessageEnvelope envelope = new MessageEnvelope(buffer.getWrittenData(), 1);
        envelopeAddressingService.address(envelope, message.getTargetPeerId());
        return envelope;
    }
}
