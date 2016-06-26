package pl.warp.engine.core.scene.script;

import pl.warp.engine.core.scene.Script;

import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptContext {

    private Set<Script<?>> scripts;

    public void addScript(Script<?> script) {
        scripts.add(script);
    }

    public void removeScript(Script<?> script) {
        scripts.remove(script);
    }

    public Set<Script<?>> getScripts() {
        return scripts;
    }
}
