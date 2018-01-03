package pl.warp.engine.core.context;

import pl.warp.engine.core.component.ComponentRegistry;
import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.component.SceneHolder;
import pl.warp.engine.core.event.EventDispatcher;
import pl.warp.engine.core.runtime.EngineLauncher;
import pl.warp.engine.core.script.ScriptManager;

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
