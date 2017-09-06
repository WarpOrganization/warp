package pl.warp.engine.core.script;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.ComponentDeathEvent;
import pl.warp.engine.core.component.SimpleListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 * Created 2016-06-26 at 21
 */
public class ScriptManager {

    private Set<Script> scripts = new HashSet<>();
    private Set<Script> scriptsToAdd = new HashSet<>();
    private Set<Script> scriptsToRemove = new HashSet<>();

    public synchronized void addScript(Script script) {
        scriptsToAdd.add(script);
        createDeathListener(script);

    }

    private void createDeathListener(Script script) {
        SimpleListener.createListener(
                script.getOwner(),
                ComponentDeathEvent.COMPONENT_DEATH_EVENT_NAME,
                (e) -> removeScript(script)
        );
    }

    public synchronized void removeScript(Script script) {
        scriptsToRemove.add(script);

    }

    public void initializeScript(Script script) {
        try {
            script.onInit();
            script.setInitialized(true);
        } catch (Exception e) {
            script.setInitialized(false);
            removeScript(script);
            throw new ScriptInitializationException(e);
        }
    }

    public Set<Script> getScripts() {
        return scripts;
    }

    public synchronized void update() {
        scripts.addAll(scriptsToAdd);
        if (!scripts.containsAll(scriptsToRemove))
            throw new ScriptNotFoundException("Unable to remove a script.");
        else scripts.removeAll(scriptsToRemove);
    }

    public synchronized void removeComponentScripts(Component component) {
        for (Script script : scripts) {
            if (script.getOwner() == component)
                removeScript(script);
        }

    }
}
