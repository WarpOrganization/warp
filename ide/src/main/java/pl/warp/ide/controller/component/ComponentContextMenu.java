package pl.warp.ide.controller.component;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import pl.warp.game.scene.GameComponent;
import pl.warp.ide.IconUtil;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 15
 */
public class ComponentContextMenu extends ContextMenu {

    private ComponentController componentController;
    private GameComponent component;
    private MenuItem addItem;
    private MenuItem removeItem;
    private MenuItem editItem;

    public ComponentContextMenu(ComponentController componentController, GameComponent component) {
        this.componentController = componentController;
        this.addItem = new MenuItem("Add child", IconUtil.getIcon("Add"));
        this.removeItem = new MenuItem("Remove", IconUtil.getIcon("Remove"));
        this.editItem = new MenuItem("Edit", IconUtil.getIcon("Edit"));
        getItems().addAll(addItem, removeItem, editItem);
        this.component = component;
        registerHandlers();
    }

    private void registerHandlers() {
        addItem.setOnAction(a -> componentController.addChild(component));
        removeItem.setOnAction(a -> componentController.removeComponent(component));
        editItem.setOnAction(a -> componentController.editComponent(component));
    }


}
