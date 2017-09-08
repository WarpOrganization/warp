package pl.warp.engine.core.script;

import org.apache.log4j.Logger;
import pl.warp.engine.core.execution.task.EngineTask;
import pl.warp.engine.core.script.updatescheduler.UpdateScheduler;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptTask extends EngineTask {

    public static final Logger SCRIPT_TASK_LOGGER = Logger.getLogger(ScriptTask.class);

    private ScriptRegistry manager;

    public ScriptTask(ScriptRegistry manager) {
        this.manager = manager;
    }

    @Override
    protected void onInit() {
        SCRIPT_TASK_LOGGER.info("Initializing script task...");
        manager.update();
        manager.getScripts().forEach(s -> {
            if (s.isInitialized())
                throw new IllegalStateException("Unable to initialize script - script has already been initialized." +
                        " There can be only one script task per context.");
            initialize(s);
        });
        SCRIPT_TASK_LOGGER.info("Script task initialized.");
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        manager.update();
        manager.getScripts().forEach(s -> {
            if (!s.isInitialized())
                initialize(s);
            try {
                UpdateScheduler scheduler = s.getScheduler();
                int updates = scheduler.pollUpdates(delta);
                if(updates > 0) {
                    int deltaPerUpdate = delta / updates;
                    for (int i = 0; i < updates; i++)
                        s.onUpdate(deltaPerUpdate); //todo fix delta
                }
            } catch (Exception e) {
                SCRIPT_TASK_LOGGER.error("Exception occurred when updating the script.", e);
            }
        });
    }

    protected void initialize(Script s) {
        try {
            manager.initializeScript(s);
        } catch (Exception e) {
            SCRIPT_TASK_LOGGER.error("Failed to initialize script.", e);
        }
    }

}
