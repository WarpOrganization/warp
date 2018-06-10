package net.warpgame.engine.core.script;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 * Created 2017-09-17 at 01
 */
//TODO rethink
@Service
public class ScriptRegistry {

    private Set<Script> scripts = new HashSet<>();
    private Set<Script> scriptsToAdd = new HashSet<>();
    private Set<Script> scriptsToRemove = new HashSet<>();

    public synchronized void addScript(Script script) {
        scriptsToAdd.add(script);
    }

    public synchronized Set<Script> getScripts() {
        return scripts;
    }

    public synchronized void removeScript(Script script) {
        scriptsToRemove.add(script);
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
