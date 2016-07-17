package pl.warp.engine.core.scene.script;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.ComponentDeathEvent;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleListener;

import java.util.HashSet;
import java.util.Set;
import java.util.jar.Pack200;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptContext {

    private Set<Script<?>> scripts = new HashSet<>();
    private Set<Script<?>> scriptsToAdd = new HashSet<>();
    private Set<Script<?>> scriptsToRemove = new HashSet<>();

    private final Object scriptsMutex = new Object();

    public void addScript(Script<?> script) {
        synchronized (scriptsMutex) {
            scriptsToAdd.add(script);
            createDeathListener(script);
        }
    }

    private void createDeathListener(Script<?> script) {
        SimpleListener.createListener(
                script.getOwner(),
                ComponentDeathEvent.COMPONENT_DEATH_EVENT_NAME,
                (e) -> removeScript(script)
        );
    }

    public void removeScript(Script<?> script) {
        synchronized (scriptsMutex) {
            scriptsToRemove.add(script);
        }
    }

    public Set<Script<?>> getScripts() {
        return scripts;
    }

    public void update() {
        synchronized (scriptsMutex) {
            scripts.addAll(scriptsToAdd);
            if (!scripts.containsAll(scriptsToRemove))
                throw new ScriptNotFoundException("Unable to remove a script.");
            else scripts.removeAll(scriptsToRemove);
        }
    }

    public void removeComponentScripts(Component component) {
        for (Script<?> script : scripts) {
            if (script.getOwner() == component)
                scriptsToRemove.add(script);
        }
    }
}
