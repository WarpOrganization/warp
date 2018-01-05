package net.warpgame.launcher;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public class WorkerRunningException extends RuntimeException {
    public WorkerRunningException(String message){
        super(message);
    }
}
