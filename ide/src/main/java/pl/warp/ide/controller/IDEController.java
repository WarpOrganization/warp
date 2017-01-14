package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jaca777
 *         Created 2017-01-14 at 18
 */
public class IDEController implements Initializable{



    @FXML
    private Button runButton;

    @FXML
    private TreeView<?> projectTree;

    @FXML
    private Canvas sceneView;

    @FXML
    private TextArea logOutput;

    @FXML
    private TreeView<?> sceneTree;

    @FXML
    private ListView<?> componentList;

    @FXML
    private Button editComponent;

    @FXML
    private Button deleteComponent;

    @FXML
    private Button createComponent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onCreateComponent(ActionEvent event) {

    }

    @FXML
    void onDeleteComponent(ActionEvent event) {

    }

    @FXML
    void onEditComponent(ActionEvent event) {

    }

    @FXML
    void onRunGame(ActionEvent event) {

    }


}
