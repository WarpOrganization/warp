package pl.warp.ide.scene.tree.descriptor;

import pl.warp.engine.core.scene.Component;
import pl.warp.ide.scene.tree.ComponentLook;

import java.util.function.Function;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 01
 */
public class ComponentTypeLook {
    private Function<Component, Boolean> typeFilter;
    private ComponentLook descriptor;

    public ComponentTypeLook(Function<Component, Boolean> typeFilter, ComponentLook descriptor) {
        this.typeFilter = typeFilter;
        this.descriptor = descriptor;
    }

    public boolean applies(Component c) {
        return typeFilter.apply(c);
    }

    public ComponentLook getDescriptor() {
        return descriptor;
    }
}
