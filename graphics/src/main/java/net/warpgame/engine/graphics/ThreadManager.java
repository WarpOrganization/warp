package net.warpgame.engine.graphics;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.graphics.memory.ResourceLoadThread;
import net.warpgame.engine.graphics.rendering.RecordingThread;
import net.warpgame.engine.graphics.rendering.RenderThread;

/**
 * @author MarconZet
 * Created 12.01.2020
 */
@Service
@Profile("graphics")
public class ThreadManager {
    private ResourceLoadThread resourceLoadThread;
    private RecordingThread recordingThread;
    private RenderThread renderThread;

    public ThreadManager(ResourceLoadThread resourceLoadThread, RecordingThread recordingThread, RenderThread renderThread) {
        this.resourceLoadThread = resourceLoadThread;
        this.recordingThread = recordingThread;
        this.renderThread = renderThread;
    }

    public void waitForThreads(){
        waitForThread(recordingThread);
        waitForThread(renderThread);
        waitForThread(resourceLoadThread);
    }

    private void waitForThread(SyncEngineThread syncEngineThread){
        Thread thread = syncEngineThread.getEngineThread();
        if(thread.isAlive()){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
