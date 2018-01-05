package net.warpgame.launcher;

/**
 * @author Hubertus
 *         Created 25.03.17
 */
public interface Updater<T> {
    void update(T data, boolean full, UpdateStatus status, Remote remote);
}
