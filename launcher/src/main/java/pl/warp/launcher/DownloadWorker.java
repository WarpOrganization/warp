package pl.warp.launcher;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Hubertus
 *         Created 21.03.17
 */
public class DownloadWorker {
    private Remote remote;
    private boolean working = false;
    private boolean killWorker = false;
    Queue<DownloadTask> downloadQueue = new LinkedList<>();
    Object key;

    public DownloadWorker(Remote remote) {
        this.remote = remote;
    }

    public DownloadWorker startWorker() {
        if (working) throw new WorkerRunningException("Worker is already running!");
        working = true;
        killWorker = false;
        new Thread(() -> {
            while (!killWorker) {
                if (!downloadQueue.isEmpty()) {
                    remote.getFile(downloadQueue.poll());
                } else {
                    done();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return this;
    }

    public void stopWorker() {
        killWorker = true;
    }

    public void addFileToQueue(DownloadTask downloadTask) {
        downloadQueue.add(downloadTask);
    }

    public Remote getRemote() {
        return remote;
    }

    public void setRemote(Remote remote) {
        this.remote = remote;
    }

    public boolean isWorking() {
        return working;
    }

    public void notifyWhenDone(Object key) {
        this.key = key;
    }

    private void done() {
        if (key != null) {
            synchronized (key) {
                key.notifyAll();
                key = null;
            }
        }
    }
}
