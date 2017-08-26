package pl.warp.engine.graphics.gui;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2017-03-11 at 14
 */
public class GuiProperty extends Property<Component> {
    public static final String GUI_PROPERTY_NAME = "guiProperty";
    private Node root;

    public GuiProperty(Node root) {
        super(GUI_PROPERTY_NAME);
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }
}
