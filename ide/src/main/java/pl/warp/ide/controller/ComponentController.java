package pl.warp.ide.controller;

import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.ide.scene.ComponentItem;
import pl.warp.ide.scene.SceneLoader;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentController {
    private TreeView<ComponentItem<Component>> sceneTree;
    private Scene scene;
    private SceneLoader loader;
    private Component selectedComponent;


    public ComponentController(TreeView<ComponentItem<Component>> sceneTree, Scene scene, SceneLoader loader) {
        this.sceneTree = sceneTree;
        this.loader = loader;
        this.scene = scene;
        this.selectedComponent = null;
        initialize();
    }

    private void initialize() {
        loader.loadScene(scene, sceneTree);
        sceneTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(selectedComponent != null)
                unmarkSelected(selectedComponent);
            selectedComponent = newValue.getValue().getComponent();
            if(selectedComponent != null)
                markSelected(selectedComponent);
        });
    }

    private void markSelected(Component selectedComponent) {
        selectedComponent.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(p -> p.getMaterial().setBrightness(4.0f));
    }

    private void unmarkSelected(Component selectedComponent) {
        selectedComponent.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(p -> p.getMaterial().setBrightness(1.0f));
    }

    public void onAddComponent() {

    }

    public void onEditComponent() {

    }

    public void onRemoveComponent() {

    }

    public void onReloadScene() {
        loader.loadScene(scene, sceneTree);
    }
}
