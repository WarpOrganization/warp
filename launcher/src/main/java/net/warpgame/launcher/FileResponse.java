package net.warpgame.launcher;

import java.io.InputStream;

/**
 * @author Hubertus
 *         Created 16.03.17
 */
public class FileResponse {
    InputStream inputStream;
    int responseCode;
    String path;

    public FileResponse(int responseCode){
        this.responseCode = responseCode;
    }

    public FileResponse(int responseCode, InputStream inputStream,String path){
        this.responseCode = responseCode;
        this.inputStream = inputStream;
        this.path = path;
    }
}
