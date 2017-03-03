package pl.warp.ide.controller.look;

import java.util.function.Function;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 01
 */
public class ItemTypeLook<T> {
    private Function<T, Boolean> typeFilter;
    private ItemLook<T> descriptor;

    public ItemTypeLook(Function<T, Boolean> typeFilter, ItemLook<T> descriptor) {
        this.typeFilter = typeFilter;
        this.descriptor = descriptor;
    }

    public boolean applies(T t) {
        return t != null && typeFilter.apply(t);
    }

    public ItemLook<T> getDescriptor() {
        return descriptor;
    }
}
