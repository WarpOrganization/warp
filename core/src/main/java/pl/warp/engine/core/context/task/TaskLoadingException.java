package pl.warp.engine.core.context.task;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */
public class TaskLoadingException extends RuntimeException {

    private Object task;

    public TaskLoadingException(String message, Object task) {
        super("Couldn't load task of type " + task.getClass().getName() + ". " + message);
        this.task = task;
    }

    public Object getTask() {
        return task;
    }
}
