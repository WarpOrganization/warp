package net.warpgame.engine.audio;

public class AudioSourcePropertyNotInitialisedException extends RuntimeException {
    public AudioSourcePropertyNotInitialisedException(String action) {
        super("You can't perform " + action + " action before assigning owner");
    }
}
