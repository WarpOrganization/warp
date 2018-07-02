package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionTools;
import net.warpgame.engine.net.event.ConfirmableNetworkEvent;
import net.warpgame.engine.net.event.EventEnvelope;
import net.warpgame.engine.net.event.EventSerializer;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
public class EventQueue extends MessageSource<EventEnvelope> {

    private final EnvelopeAddressingService envelopeAddressingService;
    private EventSerializer eventSerializer;
    private ConnectionTools connectionTools;

    public EventQueue(MessageQueue messageQueue,
                      EnvelopeAddressingService envelopeAddressingService,
                      ConnectionTools connectionTools) {
        super(messageQueue);
        this.envelopeAddressingService = envelopeAddressingService;
        this.connectionTools = connectionTools;
        eventSerializer = new EventSerializer();
    }

    @Override
    MessageEnvelope toAddressedEnvelope(EventEnvelope message) {
        //TODO some reflection magic for message type runtime generation.
        MessageEnvelope addressedEnvelope;
        message.getEvent().setSourceId(connectionTools.getPeerId());
        if (message.getEvent() instanceof ConfirmableNetworkEvent)
            addressedEnvelope = new MessageEnvelope(eventSerializer.serialize(message),
                    0,
                    ((ConfirmableNetworkEvent) message.getEvent()).getConfirmationConsumer());
        else
            addressedEnvelope = new MessageEnvelope(eventSerializer.serialize(message), 0);
        envelopeAddressingService.address(addressedEnvelope, message.getEvent().getTargetClientId());
        return addressedEnvelope;
    }
}
