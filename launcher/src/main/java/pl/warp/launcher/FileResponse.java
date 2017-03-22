package pl.warp.launcher;

import java.io.BufferedInputStream;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public class FileResponse {
    BufferedInputStream inputStream;
    int responseCode;

    public FileResponse(int responseCode){
        this.responseCode = responseCode;
    }

    public FileResponse(int responseCode, BufferedInputStream inputStream){
        this.inputStream = inputStream;
    }
}
