package pl.warp.ide.scene.tree;

import javafx.scene.control.TreeCell;
import pl.warp.game.scene.GameComponent;
import pl.warp.ide.controller.component.ComponentContextMenu;
import pl.warp.ide.controller.component.ComponentController;
import pl.warp.ide.scene.tree.look.ComponentLookRepository;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 12
 */
public class ComponentCell extends TreeCell<GameComponent> {

    private ComponentLookRepository lookRepository;
    private ComponentController componentController;

    public ComponentCell(ComponentLookRepository lookRepository, ComponentController componentController) {
        this.lookRepository = lookRepository;
        this.componentController = componentController;
    }

    @Override
    protected void updateItem(GameComponent item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu(new ComponentContextMenu(componentController, item));
            ComponentLook look = lookRepository.getLook(item);
            setText(look.createName(item));
            setGraphic(look.createImage());
        } else {
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        }
    }
}
