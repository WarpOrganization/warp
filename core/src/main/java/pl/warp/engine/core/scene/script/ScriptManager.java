package pl.warp.engine.core.scene.script;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.ComponentDeathEvent;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 21
 */
public class ScriptManager {

    private Set<Script<?>> scripts = new HashSet<>();
    private Set<Script<?>> scriptsToAdd = new HashSet<>();
    private Set<Script<?>> scriptsToRemove = new HashSet<>();

    private final Object scriptContextMutex = new Object();

    public void addScript(Script<?> script) {
        synchronized (scriptContextMutex) {
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
        synchronized (scriptContextMutex) {
            scriptsToRemove.add(script);
        }
    }

    public Set<Script<?>> getScripts() {
        return scripts;
    }

    public void update() {
        synchronized (scriptContextMutex) {
            scripts.addAll(scriptsToAdd);
            if (!scripts.containsAll(scriptsToRemove))
                throw new ScriptNotFoundException("Unable to remove a script.");
            else scripts.removeAll(scriptsToRemove);
        }
    }

    public void removeComponentScripts(Component component) {
        synchronized (scriptContextMutex) {
            for (Script<?> script : scripts) {
                if (script.getOwner() == component)
                    removeScript(script);
            }
        }
    }
}