package pl.warp.ide.scene.tree;

import javafx.application.Platform;
import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.listenable.ChildAddedEvent;
import pl.warp.engine.core.scene.listenable.ChildRemovedEvent;
import pl.warp.engine.core.scene.listenable.ListenableParent;
import pl.warp.ide.engine.IDEComponentProperty;
import pl.warp.ide.scene.tree.look.ComponentLookRepository;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2017-01-17.
 */
public class SceneTreeLoader {

    private static final int RELOAD_DELAY = 2000;

    private volatile boolean sceneChanged = false;
    private ComponentLookRepository descRepository;
    private ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(1);

    public SceneTreeLoader(ComponentLookRepository descRepository) {
        this.descRepository = descRepository;
    }

    public void loadScene(Scene scene, TreeView<ComponentItem<Component>> sceneTree) {
        ComponentTreeItem<Component> sceneItem = new ComponentTreeItem<>(scene, descRepository.getDesc(scene));
        sceneTree.setRoot(sceneItem);
        scene.forEachChildren(c -> loadComponent(sceneItem, c));
        scheduledExecutor.schedule(() -> {
            if (sceneChanged) {
                sceneChanged = false;
                Platform.runLater(() -> loadScene(scene, sceneTree));
            }
        }, RELOAD_DELAY, TimeUnit.MILLISECONDS);
    }

    private void loadComponent(ComponentTreeItem<Component> parent, Component component) {
        if(isIDEComponent(component)) return;
        ComponentTreeItem<Component> item = new ComponentTreeItem<>(component, descRepository.getDesc(component));
        if (ListenableParent.class.isAssignableFrom(component.getClass()))
            createListener(component);
        parent.getChildren().add(item);
        component.forEachChildren(c -> loadComponent(item, c));
    }

    private boolean isIDEComponent(Component component) {
        return component.hasProperty(IDEComponentProperty.IDE_COMPONENT_PROPERTY_NAME);
    }

    private void createListener(Component component) {
        SimpleListener.<ChildAddedEvent>createListener(component, ChildAddedEvent.CHILD_ADDED_EVENT_NAME, (c) -> sceneChanged = true);
        SimpleListener.<ChildRemovedEvent>createListener(component, ChildRemovedEvent.CHILD_REMOVED_EVENT_NAME, (c) -> sceneChanged = true);
    }

}
