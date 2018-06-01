package net.warpgame.engine.net.message;

import io.netty.buffer.ByteBuf;
import org.nustaq.serialization.FSTConfiguration;

/**
 * @author Hubertus
 * Created 02.01.2018
 */
public class BasicMessageDeserializer {
    private FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    public Object deserialize(ByteBuf eventContent) {
        byte[] bytes = new byte[eventContent.readableBytes()];
        eventContent.readBytes(bytes);
        return conf.asObject(bytes);
    }
}
