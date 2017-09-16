package pl.warp.engine.core.script.blueprint;

import pl.warp.engine.core.script.Script;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
public class ScriptBlueprint {
    private MethodHandle builder;
    private Consumer<Script>[] initializers;

    public ScriptBlueprint(MethodHandle builder, Consumer<Script>[] initializers) {
        this.builder = builder;
        this.initializers = initializers;
    }

    public MethodHandle getBuilder() {
        return builder;
    }

    public Consumer<Script>[] getInitializers() {
        return initializers;
    }
}
