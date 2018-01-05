package net.warpgame.launcher;

import net.warpgame.launcher.fileStructure.Element;
import net.warpgame.launcher.fileStructure.FileStructure;
import net.warpgame.launcher.fileStructure.FolderElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author Hubertus
 *         Created 31.03.17
 */
public class BasicUpdater implements Updater<PathData> {

    //TODO IO exception checking
    //TODO non-full updates

    @Override
    public void update(PathData data, boolean full, UpdateStatus status, Remote remote) {

        new Thread(() -> {
            //setup local file structure
            status.setState(UpdateStatus.STATE_GENERATING_LOCAL_STRUCTURE);
            File structureFile = new File(data.getLauncherDataPath() + "/structure.xml");
            if (full) structureFile.delete();

            FileStructure localFileStructure = new FileStructure();
            if (!structureFile.isFile()) {
                localFileStructure.generate(data);
            } else {
                localFileStructure.importStructure(data);
            }

            //get remote file structure
            status.setState(UpdateStatus.STATE_DOWNLOADING);

            DownloadWorker downloadWorker = new DownloadWorker(remote).startWorker();
            DownloadTask downloadTask = new DownloadTask("structure.xml", (res) -> {
                File outFile = new File(data.getLauncherDataPath() + "/remoteStructure.xml");
                if (outFile.exists() && outFile.isFile()) outFile.delete();
                try {
                    Files.copy(res.inputStream, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            downloadWorker.addFileToQueue(downloadTask);
            waitForWorker(downloadWorker);

            FileStructure remoteFileStructure = new FileStructure();
            remoteFileStructure.importStructure(data.getLauncherDataPath() + "/remoteStructure.xml");
            //compare and download missing
            if (localFileStructure.getMainFolder().equalsElement(remoteFileStructure.getMainFolder()))
                compareAndDownload(localFileStructure.getMainFolder(), remoteFileStructure.getMainFolder(), downloadWorker);
            else download(remoteFileStructure.getMainFolder(), downloadWorker);

            waitForWorker(downloadWorker);
            downloadWorker.stopWorker();
        }).start();
    }

    private void waitForWorker(DownloadWorker downloadWorker){
        Object key = new Object();
        synchronized (key){
            downloadWorker.notifyWhenDone(key);
            try {
                key.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void compareAndDownload(FolderElement local, FolderElement remote, DownloadWorker worker) {
        remote.getChildren().forEach(element -> {
            boolean found = false;
            for (int i = 0; i < local.getChildren().size(); i++) {
                if (element.equalsElement(local.getChildren().get(i))) {
                    if (remote.isDirectory())
                        compareAndDownload((FolderElement) local.getChildren().get(i), (FolderElement) element, worker);
                    local.getChildren().remove(i);
                    found = true;
                    break;
                }
            }
            if (!found)
                download(element, worker);
        });

        local.getChildren().forEach(this::delete);
    }

    private void download(Element element, DownloadWorker downloadWorker) {
        if (element.isFile())
            downloadWorker.addFileToQueue(new DownloadTask(element.getPath(), this::saveFile));
        else if (element.isDirectory()) {
            FolderElement folderElement = (FolderElement) element;
            for (Element element1 : folderElement.getChildren()) {
                download(element1, downloadWorker);
            }
        }
    }

    private void delete(Element element) {
        if (element.isDirectory()) {
            FolderElement folderElement = (FolderElement) element;
            folderElement.getChildren().forEach(this::delete);
        }
        new File(element.getPath()).delete();
    }

    private void saveFile(FileResponse response) {
        File outFile = new File(response.path);
        if (outFile.exists() && outFile.isFile()) outFile.delete();
        outFile.getParentFile().mkdirs();
        try {
            Files.copy(response.inputStream, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
