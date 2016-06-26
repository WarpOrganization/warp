package pl.warp.engine.core.scene.script;

import pl.warp.engine.core.EngineTask;

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

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(long delta) {
        context.getScripts().forEach(s -> s.onUpdate(delta));
    }
}
