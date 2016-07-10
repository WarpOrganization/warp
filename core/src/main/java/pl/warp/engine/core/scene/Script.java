package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

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
        context.getScriptContext().addScript(this);
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
}
