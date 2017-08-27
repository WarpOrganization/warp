package pl.warp.engine.core.script;

import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 15
 */
public abstract class Script<T extends Component> {

    private T owner = null;
    private EngineContext context = null;
    private boolean initialized;

    public Script(T owner) {
        this.owner = owner;
        this.context = owner.getContext();
        context.getScriptManager().addScript(this);
    }

    public T getOwner() {
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
}
