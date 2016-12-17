package pl.warp.engine.graphics.metadata.loader;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 20
 */
public class MetadataLoadingException extends RuntimeException {
    public MetadataLoadingException(String message) {
        super(message);
    }

    public MetadataLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
