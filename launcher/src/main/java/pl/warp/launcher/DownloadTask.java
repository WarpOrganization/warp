package pl.warp.launcher;

import java.util.function.Consumer;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public class DownloadTask {

    private String path;
    private Consumer<FileResponse> callback;
    private long fileSize;

    public DownloadTask(String path, Consumer<FileResponse> callback) {
        this.path = path;
        this.callback = callback;
    }

    public DownloadTask(String path, Consumer<FileResponse> callback, long fileSize) {
        this.path = path;
        this.callback = callback;
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public Consumer<FileResponse> getCallback() {
        return callback;
    }

    public long getFileSize() {
        return fileSize;
    }
}
