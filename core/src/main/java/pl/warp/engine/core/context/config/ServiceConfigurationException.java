package pl.warp.engine.core.context.config;

/**
 * @author Jaca777
 * Created 2017-09-23 at 15
 */
public class ServiceConfigurationException extends RuntimeException{


    public ServiceConfigurationException(String message, Object service) {
        super(getMessage(message, service));
    }

    public ServiceConfigurationException(Throwable cause) {
        super(cause);
    }

    public ServiceConfigurationException(String message) {
        super(message);
    }

    public ServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    private static String getMessage(String message, Object service) {
        return "Unable to process service " + service.getClass().getName() + " configuration." + message;
    }

}
