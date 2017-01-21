package pl.warp.ide.scene;

import javafx.scene.Node;

import java.util.function.Supplier;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentDescriptor {
    private String name;
    private Supplier<Node> image;

    public ComponentDescriptor(String name, Supplier<Node> image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Node createImage() {
        return image.get();
    }
}
