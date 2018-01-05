package net.warpgame.engine.core.context.config;

import net.warpgame.engine.core.execution.Executor;

import java.lang.invoke.MethodHandle;

/**
 * @author Jaca777
 * Created 2017-09-23 at 17
 */
class RegisteredObserver {
    private MethodHandle handle;
    private Executor executor;

    public RegisteredObserver(MethodHandle handle, Executor executor) {
        this.handle = handle;
        this.executor = executor;
    }

    public MethodHandle getHandle() {
        return handle;
    }

    public Executor getExecutor() {
        return executor;
    }
}
