package pl.warp.engine.graphics.window;

import org.apache.log4j.Logger;
import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 23
 */

@Service
@RegisterTask(thread = "graphics")
public class WindowTask extends EngineTask {

    private static final Logger logger = Logger.getLogger(WindowTask.class);

    private WindowManager windowManager;
    private Display display;

    public WindowTask(WindowManager windowManager, Config config) {
        this.windowManager = windowManager;
        this.display = config.getValue("graphics.display");
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
