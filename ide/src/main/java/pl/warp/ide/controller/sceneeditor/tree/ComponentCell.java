package pl.warp.ide.controller.sceneeditor.tree;

import javafx.scene.control.TreeCell;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.ide.controller.look.ItemLook;
import pl.warp.ide.controller.sceneeditor.ComponentContextMenu;
import pl.warp.ide.controller.sceneeditor.SceneEditorController;
import pl.warp.ide.controller.look.ItemLookRepository;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 12
 */
public class ComponentCell extends TreeCell<GameComponent> {

    private ItemLookRepository lookRepository;
    private SceneEditorController sceneEditorController;

    public ComponentCell(ItemLookRepository lookRepository, SceneEditorController sceneEditorController) {
        this.lookRepository = lookRepository;
        this.sceneEditorController = sceneEditorController;
    }

    @Override
    protected void updateItem(GameComponent item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu(new ComponentContextMenu(sceneEditorController, item));
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
