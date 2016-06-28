package pl.warp.game;

import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.script.ScriptContext;
import pl.warp.engine.graphics.RenderingTask;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 14
 */
public class Test {
    public static void main(String... args) {
        EngineContext c = new EngineContext() {
            @Override
            public Scene getScene() {
                //TODO
                throw new UnsupportedOperationException();
            }

            @Override
            public ScriptContext getScriptContext() {
                //TODO
                throw new UnsupportedOperationException();
            }
        };
        EngineThread thread = new SyncEngineThread(new SyncTimer(1), new RapidExecutionStrategy());
        thread.scheduleTask(new RenderingTask(c, new Display(512, 512), new GLFWWindowManager(thread::interrupt)));
        thread.start();
    }
}
