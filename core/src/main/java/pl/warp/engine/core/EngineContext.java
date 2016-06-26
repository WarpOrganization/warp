package pl.warp.engine.core;

import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.script.ScriptContext;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 15
 */
public interface EngineContext {
    Scene getScene();
    ScriptContext getScriptContext();
}
