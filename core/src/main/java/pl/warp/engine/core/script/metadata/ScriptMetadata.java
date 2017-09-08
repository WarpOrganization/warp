package pl.warp.engine.core.script.metadata;

import pl.warp.engine.core.script.Script;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
public class ScriptMetadata {
    private MethodHandle builder;
    private Consumer<? extends Script>[] initializers;

    public ScriptMetadata(MethodHandle builder, Consumer<? extends Script>[] initializers) {
        this.builder = builder;
        this.initializers = initializers;
    }

    public MethodHandle getBuilder() {
        return builder;
    }

    public Consumer<? extends Script>[] getInitializers() {
        return initializers;
    }
}
