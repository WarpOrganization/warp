package pl.warp.ide.controller.component;

import javafx.application.Platform;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.game.GameContext;
import pl.warp.game.scene.GameComponent;
import pl.warp.ide.controller.component.positioner.ComponentPositioner;
import pl.warp.ide.scene.tree.SceneTreeLoader;
import pl.warp.ide.scene.tree.prototype.ComponentPrototype;
import pl.warp.ide.scene.tree.prototype.PrototypeRepository;

/**
 * Created by user on 2017-01-17.
 */
public class ComponentController {

    private TreeView<GameComponent> sceneTree;
    private GameContext context;
    private SceneTreeLoader loader;
    private GameComponent selectedComponent;
    private PrototypeRepository repository;
    private ComponentPositioner positioner;
    private ComponentDescriptor descriptor;

    public ComponentController(TreeView<GameComponent> sceneTree, GridPane descriptorPane, GameContext context, SceneTreeLoader loader, PrototypeRepository repository) {
        this.sceneTree = sceneTree;
        this.loader = loader;
        this.context = context;
        this.selectedComponent = null;
        this.repository = repository;
        this.positioner = new ComponentPositioner(context);
        this.descriptor = new ComponentDescriptor(descriptorPane);
        initialize();
    }

    private void initialize() {
        loader.loadScene(context.getScene(), sceneTree, this);
        sceneTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                descriptor.unload();
                if (selectedComponent != null)
                    unmarkSelected(selectedComponent);
                if (newValue != null) {
                    descriptor.load(newValue.getValue());
                    selectedComponent = newValue.getValue();
                    markSelected(selectedComponent);
                }
            });
        });
        new ComponentSelectionScript(context.getScene(), sceneTree);
    }


    private void markSelected(GameComponent selectedComponent) {
        selectedComponent.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(p -> p.getMaterial().setBrightness(4.0f));
    }

    private void unmarkSelected(GameComponent selectedComponent) {
        selectedComponent.<GraphicsMaterialProperty>getPropertyIfExists(GraphicsMaterialProperty.MATERIAL_PROPERTY_NAME)
                .ifPresent(p -> p.getMaterial().setBrightness(1.0f));
    }

    public void addChild(GameComponent component) {
        ComponentSelectionDialog selectionMenu = new ComponentSelectionDialog(repository);
        ComponentNameDialog componentNameDialog = new ComponentNameDialog();
        selectionMenu.showAndWait().ifPresent(prototype -> nameComponent(component, componentNameDialog, prototype));
    }

    private void nameComponent(GameComponent component, ComponentNameDialog componentNameDialog, ComponentPrototype prototype) {
        componentNameDialog.showAndWait().ifPresent(name -> createComponent(component, name, prototype));
    }

    private void createComponent(GameComponent parent, String name, ComponentPrototype prototype) {
        GameComponent gameComponent = prototype.newInstance(parent);
        positionComponent(gameComponent);
        gameComponent.addProperty(new NameProperty(name));
        onReloadScene();
    }

    private void positionComponent(GameComponent gameComponent) {
        positioner.position(gameComponent);
    }

    public void editComponent(GameComponent component) {
        positionComponent(component);
    }

    public void removeComponent(GameComponent component) {
        component.destroy();
        onReloadScene();
    }

    public void onReloadScene() {
        loader.loadScene(context.getScene(), sceneTree, this);
    }
}
