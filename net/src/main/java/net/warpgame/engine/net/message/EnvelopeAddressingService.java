package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class EnvelopeAddressingService {

    public abstract void address(MessageEnvelope envelope, int targetClientId);
}
