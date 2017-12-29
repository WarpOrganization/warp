package pl.warp.net;

import org.nustaq.serialization.FSTConfiguration;

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
            return conf.asByteArray(envelope);
        }
    }
}
