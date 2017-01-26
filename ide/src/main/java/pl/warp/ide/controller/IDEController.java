package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import pl.warp.engine.core.scene.Component;
import pl.warp.ide.engine.IDEInitializer;
import pl.warp.ide.input.JavaFxInput;
import pl.warp.ide.scene.tree.ComponentItem;
import pl.warp.ide.scene.tree.SceneTreeLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 18
 */
public class IDEController implements Initializable {

    private SceneTreeLoader sceneTreeLoader;
    private JavaFxInput input;
    private IDEInitializer ideInitializer;

    public IDEController(SceneTreeLoader sceneLoader, JavaFxInput input, IDEInitializer ideInitializer) {
        this.sceneTreeLoader = sceneLoader;
        this.input = input;
        this.ideInitializer = ideInitializer;
    }

    @FXML
    private Button runButton;

    @FXML
    private TreeView<?> projectTree;

    @FXML
    private Canvas sceneView;

    @FXML
    private TextArea logOutput;

    @FXML
    private TreeView<ComponentItem<Component>> sceneTree;

    @FXML
    private ListView<?> componentList;

    @FXML
    private Button editComponent;

    @FXML
    private Button deleteComponent;

    @FXML
    private Button createComponent;

    @FXML
    private AnchorPane root;


    private ComponentController componentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ideInitializer.start(sceneView);
        componentController = new ComponentController(sceneTree, ideInitializer.getLoadedScene(), sceneTreeLoader);
        input.listenOn(root, sceneView);
    }

    @FXML
    void onAddComponent(ActionEvent event) {
        componentController.onAddComponent();
    }

    @FXML
    void onRemoveComponent(ActionEvent event) {
        componentController.onRemoveComponent();
    }

    @FXML
    void onEditComponent(ActionEvent event) {
        componentController.onEditComponent();
    }

    @FXML
    public void onReloadScene(ActionEvent event) {
        componentController.onReloadScene();
    }

    @FXML
    void onCreateRepoComponent(ActionEvent event) {

    }

    @FXML
    void onDeleteRepoComponent(ActionEvent event) {

    }

    @FXML
    void onEditRepoComponent(ActionEvent event) {

    }

    @FXML
    void onRunGame(ActionEvent event) {

    }


}
