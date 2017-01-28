package pl.warp.ide.controller.component;

import javafx.scene.control.TreeView;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.ide.scene.tree.SceneTreeLoader;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentController {

    private TreeView<GameComponent> sceneTree;
    private GameScene scene;
    private SceneTreeLoader loader;
    private GameComponent selectedComponent;


    public ComponentController(TreeView<GameComponent> sceneTree, GameScene scene, SceneTreeLoader loader) {
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
            if(newValue != null) {
                selectedComponent = newValue.getValue();
                markSelected(selectedComponent);
            }
        });
        new ComponentSelectionScript(scene, sceneTree);
    }

    private void markSelected(GameComponent selectedComponent) {
        selectedComponent.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(p -> p.getMaterial().setBrightness(4.0f));
    }

    private void unmarkSelected(GameComponent selectedComponent) {
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
