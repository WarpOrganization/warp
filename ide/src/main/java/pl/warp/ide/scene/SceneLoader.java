package pl.warp.ide.scene;

import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.ide.scene.descriptor.DescriptorRepository;

/**
 * Created by user on 2017-01-17.
 */
public class SceneLoader {

    private TreeView<Component> sceneTree;
    private DescriptorRepository descRepository;

    public SceneLoader(TreeView<Component> sceneTree, DescriptorRepository iconsRepository) {
        this.sceneTree = sceneTree;
        this.descRepository = iconsRepository;
    }

    public void loadScene(Scene scene) {
        ComponentItem<Component> sceneItem = new ComponentItem<>(scene, descRepository.getDesc(scene));
        sceneTree.setRoot(sceneItem);
        scene.forEachChildren(c -> loadComponent(sceneItem, c));
    }

    private void loadComponent(ComponentItem<Component> parent, Component component) {
        ComponentItem<Component> item = new ComponentItem<>(component, descRepository.getDesc(component));
        parent.getChildren().add(item);
        component.forEachChildren(c -> loadComponent(item, c));
    }
}
