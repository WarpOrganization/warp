package pl.warp.ide.controller.look;

import java.util.Arrays;
import java.util.List;


/**
 * Created by user on 2017-01-17.
 */
public class CustomLookRepository<T> implements ItemLookRepository<T> {

    private List<ItemTypeLook> itemTypeLooks;


    public CustomLookRepository(ItemTypeLook<T>... looks) {
        this.itemTypeLooks = Arrays.asList(looks);
    }

    @Override
    public ItemLook getLook(T t) {
        return itemTypeLooks
                .stream()
                .filter(f -> f.applies(t))
                .findAny()
                .orElseThrow(() -> new RuntimeException("No type descriptor found for item " + t + "."))
                .getDescriptor();
    }
}
