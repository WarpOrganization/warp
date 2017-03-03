package pl.warp.ide.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pl.warp.game.GameContext;
import pl.warp.game.scene.GameComponent;
import pl.warp.ide.controller.componenteditor.ComponentElement;
import pl.warp.ide.controller.componenteditor.ComponentViewSceneFactory;
import pl.warp.ide.controller.sceneeditor.SceneEditorController;
import pl.warp.ide.engine.IDEEngine;
import pl.warp.ide.input.JavaFxInput;
import pl.warp.ide.controller.sceneeditor.tree.SceneTreeLoader;
import pl.warp.ide.controller.sceneeditor.tree.prototype.PrototypeRepository;

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
    private GameContext context;
    private PrototypeRepository repository;
    private EditorManager editorManager;

    public IDEController(SceneTreeLoader sceneTreeLoader, JavaFxInput input, GameContext context, IDEEngine engine, PrototypeRepository repository) {
        this.sceneTreeLoader = sceneTreeLoader;
        this.input = input;
        this.engine = engine;
        this.context = context;
        this.repository = repository;
    }

    @FXML
    private Pane sceneTreePane;

    @FXML
    private Pane componentTreePane;

    @FXML
    private TreeView<ComponentElement> componentTree;

    @FXML
    private Pane componentPane;

    @FXML
    private TreeView<String> projectTree;

    @FXML
    private AnchorPane canvasPane;

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
    private SplitPane splitPane1;

    @FXML
    private SplitPane splitPane2;

    @FXML
    private SplitPane splitPane3;

    @FXML
    private GridPane descriptorGrid;

    private SceneEditorController sceneEditorController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createProjectTree();
        //makeCanvasResizable();
        disableSplitPanes();
        engine.start(sceneView);
        sceneEditorController = new SceneEditorController(sceneTree, descriptorGrid, context, sceneTreeLoader, repository);
        PanelManager panelManager = new PanelManager(sceneTreePane, descriptorGrid, componentTree, canvasPane);
        ComponentViewSceneFactory factory = new ComponentViewSceneFactory();
        editorManager = new EditorManager(panelManager, new ViewManager(context, context.getScene(), factory), sceneEditorController);
        input.listenOn(sceneView, engine.getScene());
    }

    private void disableSplitPanes() {
        disableDivider(splitPane1);
        disableDivider(splitPane2);
        disableDivider(splitPane3);
    }

    private void disableDivider(SplitPane pane) {
        SplitPane.Divider divider = pane.getDividers().get(0);
        double pos = divider.getPosition();
        divider.positionProperty().addListener((observable, oldValue, newValue) ->
                divider.setPosition(pos));
    }

    private void createProjectTree() {
        TreeItem<String> root = new TreeItem<>("Test project");
        projectTree.setRoot(root);
        root.getChildren().add(new TreeItem<>("Scripts"));
        root.getChildren().add(new TreeItem<>("Components"));
    }

    private void makeCanvasResizable() {
        canvasPane.widthProperty().addListener((ov, oldValue, newValue) ->
                sceneView.setWidth(newValue.doubleValue()));

        canvasPane.heightProperty().addListener((ov, oldValue, newValue) ->
                sceneView.setHeight(newValue.doubleValue()));
    }


    @FXML
    public void onReloadScene(ActionEvent event) {
        sceneEditorController.onReload();
    }


}
