package pl.warp.ide.controller.look;

/**
 * Created by user on 2017-01-17.
 */
public interface ItemLookRepository <T> {
    ItemLook<T> getLook(T component);
}
