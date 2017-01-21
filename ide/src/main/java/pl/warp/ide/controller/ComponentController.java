package pl.warp.ide.controller;

import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.ide.scene.ComponentItem;
import pl.warp.ide.scene.SceneLoader;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentController {
    private TreeView<ComponentItem<Component>> sceneTree;
    private Scene scene;
    private SceneLoader loader;
    private ComponentItem selectedComponentItem;


    public ComponentController(TreeView<ComponentItem<Component>> sceneTree, Scene scene, SceneLoader loader) {
        this.sceneTree = sceneTree;
        this.loader = loader;
        this.scene = scene;
        this.selectedComponentItem = null;
        initialize();
    }

    private void initialize() {
        loader.loadScene(scene, sceneTree);
        sceneTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectedComponentItem = newValue.getValue()
        );
    }

    public void onAddComponent() {

    }

    public void onEditComponent() {

    }

    public void onRemoveComponent() {

    }
}
