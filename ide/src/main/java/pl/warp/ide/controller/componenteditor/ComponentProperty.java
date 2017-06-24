package pl.warp.ide.controller.componenteditor;

import javafx.scene.control.ContextMenu;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 14
 */
public class ComponentProperty implements ComponentElement {
    private Property<GameComponent> property;

    public ComponentProperty(Property<GameComponent> property) {
        this.property = property;
    }

    @Override
    public ContextMenu getContextMenu() {
        //TODO
        throw new UnsupportedOperationException();
    }

    public Property<GameComponent> getProperty() {
        return property;
    }
}
