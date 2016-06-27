package pl.warp.game;

import pl.warp.engine.core.EngineThread;
import pl.warp.engine.core.RapidExecutionStrategy;
import pl.warp.engine.core.SyncEngineThread;
import pl.warp.engine.core.SyncTimer;
import pl.warp.engine.graphics.RenderingTask;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 14
 */
public class Test {
    public static void main(String... args) {
        EngineThread thread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        thread.scheduleTask(new RenderingTask(new Display(512, 512), new GLFWWindowManager()));
        thread.start();
    }
}
