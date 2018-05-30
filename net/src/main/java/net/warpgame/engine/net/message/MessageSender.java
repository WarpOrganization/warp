package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class MessageSender {

    abstract void sendMessage(MessageAddressedEnvelope addressedEnvelope);

}
