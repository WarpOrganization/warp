package net.warpgame.engine.core.context;

import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.event.EventDispatcher;
import net.warpgame.engine.core.runtime.EngineLauncher;
import net.warpgame.engine.core.script.ScriptManager;

/**
 * @author Jaca777
 * Created 2016-12-04 at 14
 */
public class EngineContext {

    public static final String CODESOURCE_DIR = EngineLauncher.CODESOURCE_DIR;



    private Context context;
    private SceneHolder sceneHolder;
    private ScriptManager scriptManager;
    private EventDispatcher eventDispatcher;
    private ComponentRegistry componentRegistry;

    public EngineContext(String... profiles) {
        this.context = Context.create(profiles);
        this.scriptManager = context.findOne(ScriptManager.class).get();
        this.eventDispatcher = context.findOne(EventDispatcher.class).get();
        this.sceneHolder = context.findOne(SceneHolder.class).get();
        this.componentRegistry = context.findOne(ComponentRegistry.class).get();
        this.sceneHolder.setScene(new Scene(this));
    }

    public Scene getScene() {
        return sceneHolder.getScene();
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public Context getLoadedContext() {
        return context;
    }

    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }
}
