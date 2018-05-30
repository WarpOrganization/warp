package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class EnvelopeSigner {

    abstract void sign(MessageAddressedEnvelope envelope, int targetClientId);
}
