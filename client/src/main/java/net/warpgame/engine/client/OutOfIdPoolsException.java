package net.warpgame.engine.client;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
public class OutOfIdPoolsException extends RuntimeException{

    public OutOfIdPoolsException() {
        super("Public id pool provider was unable to provide next pool, because it has not been yet issued by the server");
    }
}
