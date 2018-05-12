package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 12.01.2018
 */
public enum ConnectionState {
    CONNECTING,
    SYNCHRONIZING,
    LOADING,
    LIVE,
    TIMED_OUT
}
