package pl.warp.ide.scene;

import javafx.scene.Node;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentDescriptor {
    private String name;
    private Node image;

    public ComponentDescriptor(String name, Node image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Node getImage() {
        return image;
    }
}
