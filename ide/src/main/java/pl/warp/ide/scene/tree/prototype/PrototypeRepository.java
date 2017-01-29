package pl.warp.ide.scene.tree.prototype;

import java.util.Collection;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 11
 */
public interface PrototypeRepository {
    Collection<ComponentPrototype> getPrototypes();

    void add(ComponentPrototype prototype);

    void remove(ComponentPrototype prototype);
}
