package pl.warp.launcher;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public class HttpsRemote implements Remote {

    private String address;

    public HttpsRemote(String address) {
        this.address = address;
    }

    @Override
    public void getFile(DownloadTask downloadTask) {
        //TODO
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
