package net.warpgame.launcher;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public interface Remote {
    void getFile(DownloadTask downloadTask);

    void setDownloadPrefix(String downloadPrefix);

    void stop();
}
