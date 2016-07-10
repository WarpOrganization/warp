package pl.warp.engine.core.scene.script;

import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Script;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptTask extends EngineTask {

    private ScriptContext context;

    public ScriptTask(ScriptContext context) {
        this.context = context;
    }

    @Override
    protected void onInit() {
        context.getScripts().forEach(s -> {
            if (s.isInitialized())
                throw new IllegalStateException("Unable to initialize script - script has already been initialized." +
                        " There can be only one script task per context.");
            s.onInit();
            s.setInitialized(true);
        });
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        context.getScripts().forEach(s -> {
            if (!s.isInitialized())
                initialize(s);
            s.onUpdate(delta);
        });
    }

    private void initialize(Script<?> s) {
        s.onInit();
        s.setInitialized(true);
    }
}
