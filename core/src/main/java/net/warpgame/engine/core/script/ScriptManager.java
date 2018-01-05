package net.warpgame.engine.core.script;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentDeathEvent;
import net.warpgame.engine.core.component.SimpleListener;
import net.warpgame.engine.core.script.blueprint.ScriptBlueprint;
import net.warpgame.engine.core.script.blueprint.ScriptBlueprintSupplier;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentDeathEvent;
import net.warpgame.engine.core.component.SimpleListener;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.script.blueprint.ScriptBlueprint;
import net.warpgame.engine.core.script.blueprint.ScriptBlueprintSupplier;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2016-06-26 at 21
 */

@Service
public class ScriptManager {

    private ScriptBlueprintSupplier blueprintSupplier;
    private ScriptRegistry registry;

    public ScriptManager(ScriptBlueprintSupplier blueprintSupplier, ScriptRegistry registry) {
        this.blueprintSupplier = blueprintSupplier;
        this.registry = registry;
    }

    public void addScript(Component component, Class<? extends Script> scriptClass) {
        try {
            Script instance = createScriptInstance(component, scriptClass);
            registry.addScript(instance);
            createDeathListener(instance);
        } catch (Throwable throwable) {
            throw new ScriptInitializationException(scriptClass, throwable);
        }
    }

    protected Script createScriptInstance(Component component, Class<? extends Script> scriptClass) throws Throwable {
        ScriptBlueprint blueprint = blueprintSupplier.getBlueprint(scriptClass);
        Script instance = (Script) blueprint.getBuilder().invoke(component);
        instance.setBlueprint(blueprint);
        return instance;
    }

    private void createDeathListener(Script script) {
        SimpleListener.createListener(
                script.getOwner(),
                ComponentDeathEvent.COMPONENT_DEATH_EVENT_NAME,
                (e) -> registry.removeScript(script)
        );
    }


    public void initializeScript(Script script) {
        try {
            invokeInitializers(script);
            script.onInit();
            script.setInitialized(true);
        } catch (Exception e) {
            script.setInitialized(false);
            registry.removeScript(script);
            throw new ScriptInitializationException(script.getClass(), e);
        }
    }

    private void invokeInitializers(Script script) {
        Consumer<Script>[] initializers = script.getBlueprint().getInitializers();
        for(Consumer<Script> initializer : initializers) {
            initializer.accept(script);
        }
    }

    public void removeScript(Script script) {
        registry.removeScript(script);
    }

    public Set<Script> getScripts() {
        return registry.getScripts();
    }

    public synchronized void update() {
        registry.update();
    }


}
