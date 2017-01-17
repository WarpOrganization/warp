package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import pl.warp.engine.core.scene.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 18
 */
public class IDEController implements Initializable {


    @FXML
    private Button runButton;

    @FXML
    private TreeView<?> projectTree;

    @FXML
    private Canvas sceneView;

    @FXML
    private TextArea logOutput;

    @FXML
    private TreeView<Component> sceneTree;

    @FXML
    private ListView<?> componentList;

    @FXML
    private Button editComponent;

    @FXML
    private Button deleteComponent;

    @FXML
    private Button createComponent;

    private ComponentController componentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        componentController = new ComponentController(sceneTree);
    }

    @FXML
    void onCreateComponent(ActionEvent event) {
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
