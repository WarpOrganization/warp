package pl.warp.ide.controller.componenteditor;

import pl.warp.engine.core.scene.Property;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 14
 */
public class PropertyEditor {
    private Property<GameComponent> property;

    public PropertyEditor(Property<GameComponent> property) {
        this.property = property;
    }


}
