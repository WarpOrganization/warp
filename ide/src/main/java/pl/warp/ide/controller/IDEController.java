package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import pl.warp.game.scene.GameComponent;
import pl.warp.ide.controller.component.ComponentController;
import pl.warp.ide.engine.IDEEngine;
import pl.warp.ide.input.JavaFxInput;
import pl.warp.ide.scene.tree.SceneTreeLoader;
import pl.warp.ide.scene.tree.prototype.PrototypeRepository;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 18
 */
public class IDEController implements Initializable {

    private SceneTreeLoader sceneTreeLoader;
    private JavaFxInput input;
    private IDEEngine engine;
    private PrototypeRepository repository;

    public IDEController(SceneTreeLoader sceneTreeLoader, JavaFxInput input, IDEEngine engine, PrototypeRepository repository) {
        this.sceneTreeLoader = sceneTreeLoader;
        this.input = input;
        this.engine = engine;
        this.repository = repository;
    }

    @FXML
    private Button runButton;

    @FXML
    private TreeView<String> projectTree;

    @FXML
    private Canvas sceneView;

    @FXML
    private TextArea logOutput;

    @FXML
    private TreeView<GameComponent> sceneTree;

    @FXML
    private Button editComponent;

    @FXML
    private Button deleteComponent;

    @FXML
    private Button createComponent;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane descriptorGrid;

    private ComponentController componentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createProjectTree();
        engine.start(sceneView);
        componentController = new ComponentController(sceneTree, descriptorGrid, engine.getScene(), sceneTreeLoader, repository);
        input.listenOn(root, sceneView, engine.getScene());
    }

    private void createProjectTree() {
        TreeItem<String> root = new TreeItem<>("Test project");
        projectTree.setRoot(root);
        root.getChildren().add(new TreeItem<>("Scripts"));
        root.getChildren().add(new TreeItem<>("Components"));
    }


    @FXML
    public void onReloadScene(ActionEvent event) {
        componentController.onReloadScene();
    }

    @FXML
    void onRunGame(ActionEvent event) {

    }


}
