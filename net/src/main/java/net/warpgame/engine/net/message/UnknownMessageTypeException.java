package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class UnknownMessageTypeException extends RuntimeException {
    public UnknownMessageTypeException(int messageType) {
        super("Could not find valid message processor for message type " + messageType);
    }
}
