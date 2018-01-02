package pl.warp.net.event.sender;

import org.nustaq.serialization.FSTConfiguration;
import pl.warp.net.event.Envelope;
import pl.warp.net.event.FastSerializable;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public class EventSerializer {

    private FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    public byte[] serialize(Envelope envelope) {
        if (envelope instanceof FastSerializable) {
            FastSerializable serializableEvent = (FastSerializable) envelope;
            return serializableEvent.serialize();
        } else {
            return conf.asByteArray(envelope.getContent());
        }
    }
}
