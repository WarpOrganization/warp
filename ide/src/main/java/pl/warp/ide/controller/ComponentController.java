package pl.warp.ide.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentController {
    private TreeView<Component> sceneTree;
    private Component selectedComponent;


    public ComponentController(TreeView<Component> sceneTree) {
        this.sceneTree = sceneTree;
        this.selectedComponent = null;
        initialize();
    }

    private void initialize() {
        sceneTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectedComponent = newValue.getValue()
        );
    }

    public void onAddComponent() {

    }

    public void onEditComponent() {

    }

    public void onRemoveComponent() {

    }
}
