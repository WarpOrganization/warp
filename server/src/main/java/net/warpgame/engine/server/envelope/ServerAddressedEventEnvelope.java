package net.warpgame.engine.server.envelope;

/**
 * @author Hubertus
 * Created 29.12.2017
 */
public class ServerAddressedEventEnvelope extends ServerAddresedEnvelope {
    @Override
    public boolean isInternal() {
        return false;
    }
}
