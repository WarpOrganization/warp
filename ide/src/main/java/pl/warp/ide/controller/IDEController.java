package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.pipeline.output.RenderingPipelineOutputHandler;
import pl.warp.ide.graphics.SceneViewRenderer;
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

    private SceneTreeLoader sceneLoader;
    private JavaFxInput input;

    public IDEController(SceneTreeLoader sceneLoader, JavaFxInput input) {
        this.sceneLoader = sceneLoader;
        this.input = input;
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

    private SceneViewRenderer sceneViewRenderer;

    public static RenderingPipelineOutputHandler INPUT; //TODO REMOVE - really bad and temporary solution
    public static Scene SCENE; //TODO REMOVE - really bad and temporary solution

    private ComponentController componentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.sceneViewRenderer = new SceneViewRenderer(sceneView, INPUT);
        this.sceneViewRenderer.startRendering();
        componentController = new ComponentController(sceneTree, SCENE, sceneLoader);
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
    void okKeyReleased(KeyEvent event) {
        input.onKeyReleased(event);
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        input.onKeyPressed(event);
    }

    @FXML
    void onMouseMoved(MouseEvent event) {
        input.onMouseMoved(event);
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        input.onMousePressed(event);
    }

    @FXML
    void onMouseReleased(MouseEvent event) {
        input.onMouseReleased(event);
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
