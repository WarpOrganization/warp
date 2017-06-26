package pl.warp.launcher;

/**
 * @author Hubertus
 *         Created 18.06.2017
 */
public class UpdateStatus {
    public static final int STATE_STARTING = 2;
    public static final int STATE_GENERATING_LOCAL_STRUCTURE = 4;
    public static final int STATE_DOWNLOADING = 8;

    private int state = STATE_STARTING;
    private int percentDone = 0;
    private int toDownload = 0;
    private int downloaded = 0;
    private int speed = 0;

    public UpdateStatus(int state, int percentDone, int toDownload, int downloaded, int speed) {
        this.state = state;
        this.percentDone = percentDone;
        this.toDownload = toDownload;
        this.downloaded = downloaded;
        this.speed = speed;
    }

    public UpdateStatus() {
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(int percentDone) {
        this.percentDone = percentDone;
    }

    public int getToDownload() {
        return toDownload;
    }

    public void setToDownload(int toDownload) {
        this.toDownload = toDownload;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
