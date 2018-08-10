package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.ConnectionTools;
import net.warpgame.engine.net.messagetypes.event.ConfirmableNetworkEvent;
import net.warpgame.engine.net.messagetypes.event.EventEnvelope;
import net.warpgame.engine.net.messagetypes.event.EventSerializer;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
public class EventMessageSource extends MessageSource<EventEnvelope> {

    private final EnvelopeAddressingService envelopeAddressingService;
    private EventSerializer eventSerializer;
    private ConnectionTools connectionTools;
    private Serializers serializers;
    private SerializationBuffer buffer = new SerializationBuffer(1500);

    public EventMessageSource(MessageQueue messageQueue,
                              EnvelopeAddressingService envelopeAddressingService,
                              ConnectionTools connectionTools,
                              Serializers serializers) {
        super(messageQueue);
        this.envelopeAddressingService = envelopeAddressingService;
        this.connectionTools = connectionTools;
        this.serializers = serializers;
        eventSerializer = new EventSerializer();
    }

    @Override
    MessageEnvelope toAddressedEnvelope(EventEnvelope message) {
        //TODO some reflection magic for message type runtime generation.
        MessageEnvelope addressedEnvelope;
        message.getEvent().setSourceId(connectionTools.getPeerId());

        buffer.setWriterIndex(0);
        buffer.write(message.getEvent().getType());
        buffer.write(message.getTargetComponent().getId());
        serializers.serialize(buffer, message.getEvent());
        if (message.getEvent() instanceof ConfirmableNetworkEvent)
            addressedEnvelope = new MessageEnvelope(buffer.getWrittenData(),
                    0,
                    ((ConfirmableNetworkEvent) message.getEvent()).getConfirmationConsumer());
        else
            addressedEnvelope = new MessageEnvelope(buffer.getWrittenData(), 0);
        envelopeAddressingService.address(addressedEnvelope, message.getEvent().getTargetClientId());
        return addressedEnvelope;
    }
}
