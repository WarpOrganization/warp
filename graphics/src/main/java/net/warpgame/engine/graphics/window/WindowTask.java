package net.warpgame.engine.graphics.window;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import org.apache.log4j.Logger;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 23
 */

@Service
@Profile("graphics")
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
