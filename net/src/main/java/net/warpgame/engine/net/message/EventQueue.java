package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
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

    public EventQueue(MessageQueue messageQueue, EnvelopeAddressingService envelopeAddressingService) {
        super(messageQueue);
        this.envelopeAddressingService = envelopeAddressingService;
        eventSerializer = new EventSerializer();
    }

    @Override
    MessageEnvelope toAddressedEnvelope(EventEnvelope message) {
        //TODO some reflection magic for message type runtime generation.
        MessageEnvelope addressedEnvelope = new MessageEnvelope(eventSerializer.serialize(message), 0);
        envelopeAddressingService.address(addressedEnvelope, message.getEvent().getTargetClientId());
        return addressedEnvelope;
    }
}
