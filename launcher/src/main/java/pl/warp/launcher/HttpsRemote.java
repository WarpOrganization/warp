package pl.warp.launcher;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public class HttpsRemote implements Remote {

    private String address;
    private String downloadPrefix = "";


    public HttpsRemote(String address) {
        this.address = address;
    }

    @Override
    public void getFile(DownloadTask downloadTask) {
        //TODO
    }

    @Override
    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    @Override
    public void stop() {
        //TODO
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
