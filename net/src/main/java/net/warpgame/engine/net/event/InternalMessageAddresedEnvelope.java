package net.warpgame.engine.net.event;

/**
 * @author Hubertus
 * Created 14.05.2018
 */
public class InternalMessageAddresedEnvelope extends AddressedEnvelope{
    @Override
    public boolean isInternal() {
        return true;
    }
}
