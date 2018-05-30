package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.event.EventEnvelope;
import net.warpgame.engine.net.event.sender.EventSerializer;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
public class EventQueue extends MessageSource<EventEnvelope> {

    private final EnvelopeSigner envelopeSigner;
    private EventSerializer eventSerializer;

    public EventQueue(MessageQueue messageQueue, EnvelopeSigner envelopeSigner) {
        super(messageQueue);
        this.envelopeSigner = envelopeSigner;
        eventSerializer = new EventSerializer();
    }

    @Override
    MessageAddressedEnvelope toAddressedEnvelope(EventEnvelope message) {
        //TODO some reflection magic for message type runtime generation.
        MessageAddressedEnvelope addressedEnvelope = new MessageAddressedEnvelope(eventSerializer.serialize(message), 0);
        envelopeSigner.sign(addressedEnvelope, message.getEvent().getTargetClientId());
        return addressedEnvelope;
    }
}
