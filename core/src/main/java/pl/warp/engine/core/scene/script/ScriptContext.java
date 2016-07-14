package pl.warp.engine.core.scene.script;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Script;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptContext {

    private Set<Script<?>> scripts = new HashSet<>();
    private Set<Script<?>> scriptsToAdd = new HashSet<>();
    private Set<Script<?>> scriptsToRemove = new HashSet<>();

    public void addScript(Script<?> script) {
        scriptsToAdd.add(script);
    }

    public void removeScript(Script<?> script) {
        scriptsToRemove.add(script);
    }

    public Set<Script<?>> getScripts() {
        return scripts;
    }

    public void update() {
        scripts.addAll(scriptsToAdd);
        if (!scripts.containsAll(scriptsToRemove))
            throw new ScriptNotFoundException("Unable to remove a script.");
        else scripts.removeAll(scriptsToRemove);
    }

    public void removeComponentScripts(Component component) {
        for (Script<?> script : scripts) {
            if (script.getOwner() == component)
                scriptsToRemove.add(script);
        }
    }
}
