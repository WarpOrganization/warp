package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.ConnectionTools;
import net.warpgame.engine.net.messagetypes.event.ConfirmableNetworkEvent;
import net.warpgame.engine.net.messagetypes.event.EventEnvelope;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
public class EventMessageSource extends MessageSource<EventEnvelope> {

    private ConnectionTools connectionTools;
    private Serializers serializers;
    private SerializationBuffer buffer = new SerializationBuffer(1500);

    public EventMessageSource(MessageQueue messageQueue,
                              EnvelopeAddressingService envelopeAddressingService,
                              ConnectionTools connectionTools,
                              Serializers serializers) {
        super(messageQueue, envelopeAddressingService);
        this.connectionTools = connectionTools;
        this.serializers = serializers;
    }

    @Override
    void serializeMessage(EventEnvelope message, EnvelopeAddressingService addressingService) {
        //TODO some reflection magic for message type runtime generation.
        message.getEvent().setSourceId(connectionTools.getPeerId());

        buffer.setWriterIndex(0);
        buffer.write(message.getEvent().getType());
        buffer.write(message.getTargetComponent().getId());
        serializers.serialize(buffer, message.getEvent());
        if (message.getEvent() instanceof ConfirmableNetworkEvent)
            addressingService.createEnvelope(
                    buffer.getWrittenData(),
                    message.getEvent().getTargetPeerId(),
                    ((ConfirmableNetworkEvent) message.getEvent()).getConfirmationConsumer());
        else
            addressingService.createEnvelope(buffer.getWrittenData(), message.getEvent().getTargetPeerId());
    }

    @Override
    int getMessageType() {
        return 0;
    }
}
