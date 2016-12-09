package pl.warp.engine.core;

import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.script.ScriptContext;

/**
 * @author Jaca777
 *         Created 2016-12-04 at 14
 */
public class EngineContext {
    private Scene scene;
    private ScriptContext scriptContext;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    public void setScriptContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }
}
