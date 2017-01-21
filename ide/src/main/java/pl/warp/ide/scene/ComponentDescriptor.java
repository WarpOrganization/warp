package pl.warp.ide.scene;

import javafx.scene.Node;
import pl.warp.engine.core.scene.Component;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentDescriptor {
    private Function<Component, String> nameSupplier;
    private Supplier<Node> imageSupplier;

    public ComponentDescriptor(Function<Component, String> nameSupplier, Supplier<Node> imageSupplier) {
        this.nameSupplier = nameSupplier;
        this.imageSupplier = imageSupplier;
    }

    public String createName(Component component) {
        return nameSupplier.apply(component);
    }

    public Node createImage() {
        return imageSupplier.get();
    }
}
