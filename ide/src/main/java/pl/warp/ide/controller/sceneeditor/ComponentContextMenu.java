package pl.warp.ide.controller.sceneeditor;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.ide.IconUtil;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 15
 */
public class ComponentContextMenu extends ContextMenu {

    private SceneEditorController sceneEditorController;
    private GameComponent component;
    private MenuItem addItem;
    private MenuItem removeItem;
    private MenuItem editItem;

    public ComponentContextMenu(SceneEditorController sceneEditorController, GameComponent component) {
        this.sceneEditorController = sceneEditorController;
        this.addItem = new MenuItem("Add child", IconUtil.getIcon("Add"));
        this.removeItem = new MenuItem("Remove", IconUtil.getIcon("Remove"));
        this.editItem = new MenuItem("Edit", IconUtil.getIcon("Edit"));
        getItems().addAll(addItem, removeItem, editItem);
        this.component = component;
        registerHandlers();
    }

    private void registerHandlers() {
        addItem.setOnAction(a -> sceneEditorController.addChild(component));
        removeItem.setOnAction(a -> sceneEditorController.removeComponent(component));
        editItem.setOnAction(a -> sceneEditorController.editComponent(component));
    }


}
