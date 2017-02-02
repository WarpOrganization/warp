package pl.warp.engine.core.scene.script;

import org.apache.log4j.Logger;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Script;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptTask extends EngineTask {

    public static final Logger SCRIPT_TASK_LOGER = Logger.getLogger(ScriptTask.class);

    private ScriptManager context;

    public ScriptTask(ScriptManager context) {
        this.context = context;
    }

    @Override
    protected void onInit() {
        context.update();
        context.getScripts().forEach(s -> {
            if (s.isInitialized())
                throw new IllegalStateException("Unable to initialize script - script has already been initialized." +
                        " There can be only one script task per context.");
            initialize(s);
        });
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        context.update();
        context.getScripts().forEach(s -> {
            if (!s.isInitialized())
                initialize(s);
            try {
                s.onUpdate(delta);
            } catch (Exception e){
                SCRIPT_TASK_LOGER.error("Exception occurred when updating the script.", e);
            }
        });
    }

    private void initialize(Script<?> s) {
        try {
            s.onInit();
            s.setInitialized(true);
        }catch (Exception e){
            SCRIPT_TASK_LOGER.error("Failed to initialize script.", e);
            s.setInitialized(false);
            context.removeScript(s);
        }
    }
}
