package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import net.warpgame.engine.net.internalmessage.InternalMessageSerializer;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class InternalMessageQueue extends MessageSource<InternalMessage> {

    private final EnvelopeAddressingService envelopeAddressingService;
    private InternalMessageSerializer internalMessageSerializer;

    public InternalMessageQueue(MessageQueue messageQueue, EnvelopeAddressingService envelopeAddressingService) {
        super(messageQueue);
        this.envelopeAddressingService = envelopeAddressingService;
        internalMessageSerializer = new InternalMessageSerializer();
    }

    @Override
    MessageEnvelope toAddressedEnvelope(InternalMessage message) {
        MessageEnvelope envelope = new MessageEnvelope(internalMessageSerializer.serialize(message), 1);
        envelopeAddressingService.address(envelope, message.getTargetPeerId());
        return envelope;
    }
}
