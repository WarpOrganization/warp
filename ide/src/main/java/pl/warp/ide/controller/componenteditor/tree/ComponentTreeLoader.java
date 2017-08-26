package pl.warp.ide.controller.componenteditor.tree;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.ide.controller.componenteditor.ComponentChild;
import pl.warp.ide.controller.componenteditor.ComponentEditorController;
import pl.warp.ide.controller.componenteditor.ComponentElement;
import pl.warp.ide.controller.componenteditor.ComponentProperty;
import pl.warp.ide.controller.look.ItemLookRepository;
import pl.warp.ide.controller.sceneeditor.IDEComponentProperty;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Jaca777
 *         Created 2017-03-03 at 15
 */
public class ComponentTreeLoader {
    private static final int RELOAD_DELAY = 2000;

    private ItemLookRepository descRepository;
    private ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(1);

    public ComponentTreeLoader(ItemLookRepository descRepository) {
        this.descRepository = descRepository;
    }

    public void loadComponent(GameComponent component, TreeView<ComponentElement> componentTree, ComponentEditorController componentEditorController) {
        componentTree.setCellFactory(param -> new ElementCell(descRepository));
        TreeItem<ComponentElement> item = new TreeItem<>(new ComponentChild(component));
        componentTree.setRoot(item);
        component.forEachGameChildren(c -> loadChildElement(item, c));
        component.getProperties().forEach(p -> loadPropertyElement(item, p));
    }

    private void loadChildElement(TreeItem<ComponentElement> parent, GameComponent child) {
        TreeItem<ComponentElement> item = new TreeItem<>(new ComponentChild(child));
        parent.getChildren().add(item);
        child.forEachGameChildren(c -> loadChildElement(item, c));
    }

    private void loadPropertyElement(TreeItem<ComponentElement> parent, Property p) {
        TreeItem<ComponentElement> item = new TreeItem<>(new ComponentProperty(p));
        parent.getChildren().add(item);
    }

    private boolean isIDEComponent(GameComponent component) {
        return component.hasProperty(IDEComponentProperty.IDE_COMPONENT_PROPERTY_NAME);
    }

}
