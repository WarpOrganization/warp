package pl.warp.engine.graphics;

import org.apache.log4j.Logger;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.WindowManager;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 23
 */
public class WindowTask extends EngineTask {

    private static final Logger logger = Logger.getLogger(WindowTask.class);

    private WindowManager windowManager;
    private Display display;

    public WindowTask(WindowManager windowManager, Display display) {
        this.windowManager = windowManager;
        this.display = display;
    }

    @Override
    protected void onInit() {
        windowManager.makeWindow(display);
        logger.info("Window created.");
    }

    @Override
    protected void onClose() {
        windowManager.closeWindow();
        logger.info("Window closed.");
    }

    @Override
    public void update(int delta) {
        windowManager.updateWindow();
    }
}
