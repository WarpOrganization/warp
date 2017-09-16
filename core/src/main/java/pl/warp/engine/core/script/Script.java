package pl.warp.engine.core.script;

import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.blueprint.ScriptBlueprint;
import pl.warp.engine.core.script.updatescheduler.UpdateScheduler;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 15
 */
public abstract class Script {

    private Component owner = null;
    private EngineContext context = null;
    private boolean initialized;
    private UpdateScheduler scheduler;
    private ScriptBlueprint blueprint;

    public Script(Component owner) {
        this.owner = owner;
        this.context = owner.getContext();
    }

    public Component getOwner() {
        return owner;
    }

    protected EngineContext getContext() {
        return context;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public abstract void onInit();

    public abstract void onUpdate(int delta);

    public void stop(){
        getContext().getScriptManager().removeScript(this);
    }

    public void setScheduler(UpdateScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public UpdateScheduler getScheduler() {
        return scheduler;
    }

    protected void log(String msg){
        System.out.println(msg); //TODO make a logger
    }

    protected ScriptBlueprint getBlueprint() {
        return blueprint;
    }

    void setBlueprint(ScriptBlueprint blueprint) {
        this.blueprint = blueprint;
    }
}
