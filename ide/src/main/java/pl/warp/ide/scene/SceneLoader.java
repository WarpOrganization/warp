package pl.warp.ide.scene;

import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.core.scene.listenable.ChildRemovedEvent;
import pl.warp.engine.core.scene.listenable.ListenableParent;
import pl.warp.ide.scene.descriptor.DescriptorRepository;

/**
 * Created by user on 2017-01-17.
 */
public class SceneLoader {

    private DescriptorRepository descRepository;

    public SceneLoader(DescriptorRepository iconsRepository) {
        this.descRepository = iconsRepository;
    }

    public void loadScene(Scene scene, TreeView<ComponentItem<Component>> sceneTree) {
        ComponentTreeItem<Component> sceneItem = new ComponentTreeItem<>(scene, descRepository.getDesc(scene));
        sceneTree.setRoot(sceneItem);
        scene.forEachChildren(c -> loadComponent(sceneItem, c, sceneTree));
    }

    private void loadComponent(ComponentTreeItem<Component> parent, Component component, TreeView<ComponentItem<Component>> sceneTree) {
        ComponentTreeItem<Component> item = new ComponentTreeItem<>(component, descRepository.getDesc(component));
        if (ListenableParent.class.isAssignableFrom(component.getClass()))
            createListener(component, parent, sceneTree);
        parent.getChildren().add(item);
        component.forEachChildren(c -> loadComponent(item, c, sceneTree));
    }

    private void createListener(Component component, ComponentTreeItem<Component> parent, TreeView<ComponentItem<Component>> sceneTree) {
        SimpleListener.<ChildAddedEvent>createListener(component, ChildAddedEvent.CHILD_ADDED_EVENT_NAME, (c) -> reload(parent, sceneTree));
        SimpleListener.<ChildRemovedEvent>createListener(component, ChildRemovedEvent.CHILD_REMOVED_EVENT_NAME, (c) -> reload(parent, sceneTree));
    }

    private void reload(ComponentTreeItem<Component> parent, TreeView<ComponentItem<Component>> sceneTree) {
        Platform.runLater(() -> {
            parent.getChildren().clear();
            TreeItem<ComponentItem<Component>> parentParent = parent.getParent();
            if (parentParent == null)
                loadScene((Scene) parent.getValue().getComponent(), sceneTree);
            else {
                Component parentComponent = parent.getValue().getComponent();
                loadComponent((ComponentTreeItem<Component>) parentParent, parentComponent, sceneTree);
            }
        });
    }
}
