package pl.warp.engine.core;

import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.script.ScriptContext;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 15
 */
public class EngineContext {
    private Scene scene;
    private ScriptContext scriptContext = new ScriptContext();

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
    }
}
