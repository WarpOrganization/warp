package pl.warp.launcher;

/**
 * @author Hubertus
 *         Created 31.03.17
 */
public class PathData {
    private String filesPath;
    private String launcherDataPath;

    public PathData(String filesPath, String launcherDataPath) {
        this.filesPath = filesPath;
        this.launcherDataPath = launcherDataPath;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public String getLauncherDataPath() {
        return launcherDataPath;
    }

    public void setLauncherDataPath(String launcherDataPath) {
        this.launcherDataPath = launcherDataPath;
    }
}
