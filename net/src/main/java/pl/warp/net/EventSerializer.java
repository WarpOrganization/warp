package pl.warp.net;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public class EventSerializer {

    FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    public byte[] serialize(RemoteEvent remoteEvent) {
        if (remoteEvent instanceof FastSerializable) {
            FastSerializable serializableEvent = (FastSerializable) remoteEvent;
            return serializableEvent.serialize();
        } else {
            return conf.asByteArray(remoteEvent);
        }
    }
}
