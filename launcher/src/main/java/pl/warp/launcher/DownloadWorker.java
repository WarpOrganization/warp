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


    public DownloadWorker(Remote remote) {
        this.remote = remote;
    }

    public void startWorker(){
        if(working) throw new WorkerRunningException("Worker is already running!");
        working = true;
        killWorker=false;
        new Thread(() -> {
            for(;;){

                if(killWorker)return;
            }
        }).start();
    }

    public void stopWorker(){
        killWorker = true;
    }

    public void addFileToQueue(){
        //TODO
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
}
