package pl.warp.engine.core.script;

/**
 * @author Jaca777
 * Created 2017-02-07 at 01
 */
public class ScriptInitializationException extends RuntimeException {
    private Class<?> scriptClass;

    public ScriptInitializationException(Class<?> scriptClass, Throwable cause) {
        this(scriptClass, "Failed to initialize script of type: " + scriptClass.getName(), cause);
    }

    public ScriptInitializationException(Class<?> scriptClass, String message, Throwable cause) {
        super(message, cause);
        this.scriptClass = scriptClass;
    }

    public ScriptInitializationException(Class<?> scriptClass, String message) {
        super("Failed to initialize script of type: " + scriptClass.getName() + ". " + message);
        this.scriptClass = scriptClass;
    }

    public Class<?> getScriptClass() {
        return scriptClass;
    }
}
