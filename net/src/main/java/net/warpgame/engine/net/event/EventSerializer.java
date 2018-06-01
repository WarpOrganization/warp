package net.warpgame.engine.net.event;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public class EventSerializer {

    private FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    //    public byte[] serialize(Envelope envelope) {
//        if (envelope instanceof FastSerializable) {
//            FastSerializable serializableEvent = (FastSerializable) envelope;
//            return serializableEvent.serialize();
//        } else {
//            return conf.asByteArray(envelope.getContent());
//
//        }
//    }

    private int[] length = new int[1];

    public byte[] serialize(EventEnvelope envelope) {
        byte[] serializedEvent;
        serializedEvent = conf.asSharedByteArray(envelope, length);
        byte[] out = new byte[serializedEvent.length + 8];

        writeInt(out, envelope.getEvent().getType(), 0);
        writeInt(out, envelope.getTargetComponent().getId(), 4);

        System.arraycopy(serializedEvent, 0, out, 8, serializedEvent.length);
        return out;
    }

    private void writeInt(byte[] out, int val, int offset) {
        out[offset] = (byte) ((val & 0xFF000000) >> 24);
        out[offset + 1] = (byte) ((val & 0x00FF0000) >> 16);
        out[offset + 2] = (byte) ((val & 0x0000FF00) >> 8);
        out[offset + 3] = (byte) ((val & 0x000000FF));

    }
}
