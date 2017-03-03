package pl.warp.ide.controller.look;

import javafx.scene.Node;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by user on 2017-01-17.
 */
public class ItemLook<T> {

    private Function<T, String> nameSupplier;
    private Supplier<Node> imageSupplier;

    public ItemLook(Function<T, String> nameSupplier, Supplier<Node> imageSupplier) {
        this.nameSupplier = nameSupplier;
        this.imageSupplier = imageSupplier;

    }

    public String createName(T component) {
        return nameSupplier.apply(component);
    }

    public Node createImage() {
        return imageSupplier.get();
    }
}
