package net.warpgame.engine.net.internalmessage;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class InternalMessageSerializer {
    private FSTConfiguration conf = FSTConfiguration.getDefaultConfiguration();

    public byte[] serialize(InternalMessage message){
        return conf.asByteArray(message);
    }
}
