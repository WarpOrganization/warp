package pl.warp.engine.core.extension;

/**
 * @author Jaca777
 *         Created 2017-06-18 at 13
 */
public interface Extendable {
    <T> T getExtension(String name);
}
