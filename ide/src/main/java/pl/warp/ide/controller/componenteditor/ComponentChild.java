package pl.warp.ide.controller.componenteditor;

import javafx.scene.control.ContextMenu;
import pl.warp.engine.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 14
 */
public class ComponentChild implements ComponentElement {
    private GameComponent component;

    public ComponentChild(GameComponent component) {
        this.component = component;
    }

    @Override
    public ContextMenu getContextMenu() {
        //TODO
        throw new UnsupportedOperationException();
    }


    public GameComponent getComponent() {
        return component;
    }
}
