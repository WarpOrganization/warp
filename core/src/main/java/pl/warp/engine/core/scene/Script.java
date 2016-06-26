package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 15
 */
public abstract class Script<T extends Component> {
    private T owner = null;
    private EngineContext context = null;

    public Script(T owner) {
        this.owner = owner;
        this.context = owner.getContext();
        owner.addScript(this);
        context.getScriptContext().addScript(this);
        onInit();
    }

    public T getOwner() {
        return owner;
    }

    protected EngineContext getContext() {
        return context;
    }

    public abstract void onInit();

    public abstract void onUpdate(long delta);
}
