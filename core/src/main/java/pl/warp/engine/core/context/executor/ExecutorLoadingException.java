package pl.warp.engine.core.context.executor;

/**
 * @author Jaca777
 * Created 2017-09-23 at 15
 */
public class ExecutorLoadingException extends RuntimeException {

    private Class<?> executorClass;

    public ExecutorLoadingException(String message, Class<?> executorClass) {
        super("Failed to load executor of type: " + executorClass.getName() + "." + message);
        this.executorClass = executorClass;
    }

    public Class<?> getExecutorClass() {
        return executorClass;
    }
}
