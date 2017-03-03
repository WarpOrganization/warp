package pl.warp.ide.controller.componenteditor.tree;

import javafx.scene.control.TreeCell;
import pl.warp.ide.controller.componenteditor.ComponentElement;
import pl.warp.ide.controller.look.ItemLook;
import pl.warp.ide.controller.look.ItemLookRepository;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 15
 */
public class ElementCell extends TreeCell<ComponentElement> {
    private ItemLookRepository lookRepository;

    public ElementCell(ItemLookRepository lookRepository) {
        this.lookRepository = lookRepository;
    }

    @Override
    protected void updateItem(ComponentElement item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu(item.getContextMenu());
            ItemLook look = lookRepository.getLook(item);
            setText(look.createName(item));
            setGraphic(look.createImage());
        } else {
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        }
    }
}
