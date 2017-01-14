package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

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
    private ListView<?> propertiesList;

    @FXML
    private TableView<?> propertyData;

    @FXML
    private Button addPropertyButton;

    @FXML
    private Button removePropertyButton;

    @FXML
    private TextArea logOutput;

    @FXML
    private TreeView<?> sceneTree;

    @FXML
    private ListView<?> componentList;

    @FXML
    private Canvas sceneView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onAddProperty(ActionEvent event) {

    }

    @FXML
    void onRemoveProperty(ActionEvent event) {

    }

    @FXML
    void onRunGame(ActionEvent event) {

    }

}
