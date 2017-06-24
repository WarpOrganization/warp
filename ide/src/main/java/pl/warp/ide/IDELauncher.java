package pl.warp.ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.game.GameContextBuilder;
import pl.warp.ide.controller.IDEController;
import pl.warp.ide.engine.IDEEngine;
import pl.warp.ide.engine.SceneViewRenderer;
import pl.warp.ide.input.JavaFxInput;
import pl.warp.ide.controller.look.ItemLook;
import pl.warp.ide.controller.sceneeditor.tree.SceneTreeLoader;
import pl.warp.ide.controller.look.ItemTypeLook;
import pl.warp.ide.controller.look.CustomLookRepository;
import pl.warp.ide.controller.look.DefaultNameSupplier;
import pl.warp.ide.controller.sceneeditor.tree.prototype.FunctionComponentPrototype;
import pl.warp.ide.controller.sceneeditor.tree.prototype.LocalPrototypeRepository;
import pl.warp.ide.controller.sceneeditor.tree.prototype.PrototypeRepository;
import pl.warp.test.SpaceSceneLoader;

/**
 * @author Jaca777
 *         Created 2016-12-04 at 11
 */
public class IDELauncher extends Application {

    public static final int FPS = 40;


    @Override
    public void start(Stage primaryStage) throws Exception {
        CustomLookRepository lookRepo = loadCustomLookRepository();
        RenderingConfig config = new RenderingConfig(FPS, new Display(false, -1, -1).setVisible(false));
        config.setLens(false);
        JavaFxInput javaFxInput = new JavaFxInput();
        GameContextBuilder contextBuilder = new GameContextBuilder();
        SceneViewRenderer renderer = new SceneViewRenderer();
        SpaceSceneLoader sceneLoader = getSceneLoader(config, contextBuilder);
        IDEEngine engine = new IDEEngine(sceneLoader, renderer, config, contextBuilder, javaFxInput);

        PrototypeRepository testRepository = createTestRepository(sceneLoader);
        IDEController controller = new IDEController(new SceneTreeLoader(lookRepo), javaFxInput, contextBuilder.getGameContext(), engine, testRepository);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "idewindow.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        javaFxInput.listenOn(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Warp Engine IDE");
        primaryStage.show();
    }

    private PrototypeRepository createTestRepository(SpaceSceneLoader sceneLoader) {
        PrototypeRepository repository = new LocalPrototypeRepository();
        FunctionComponentPrototype shipPrototype = new FunctionComponentPrototype("Ship", sceneLoader::createShip);
        FunctionComponentPrototype frigatePrototype = new FunctionComponentPrototype("Frigate", sceneLoader::createFrigate);
        FunctionComponentPrototype planetPrototype = new FunctionComponentPrototype("Planet", sceneLoader::createPlanet);
        repository.add(planetPrototype);
        repository.add(shipPrototype);
        repository.add(frigatePrototype);
        return repository;
    }

    private CustomLookRepository loadCustomLookRepository() {
        ItemLook sceneLook = new ItemLook(
                new DefaultNameSupplier("Scene"),
                () -> IconUtil.getIcon("Scene"));
        ItemLook cameraLook = new ItemLook(
                new DefaultNameSupplier("Camera"),
                () -> IconUtil.getIcon("Camera"));
        ItemLook drawableLook = new ItemLook(
                new DefaultNameSupplier("Drawable Component"),
                () -> IconUtil.getIcon("Drawable"));
        ItemLook itemLook = new ItemLook(
                new DefaultNameSupplier("Component"),
                () -> IconUtil.getIcon("Component"));
        return new CustomLookRepository<>(
                new ItemTypeLook<>(this::isScene, sceneLook),
                new ItemTypeLook<>(this::isCamera, cameraLook),
                new ItemTypeLook<>(this::isDrawableModel, drawableLook),
                new ItemTypeLook(c -> true, itemLook));
    }

    private Boolean isScene(Component component) {
        return component instanceof pl.warp.engine.core.scene.Scene;
    }

    private Boolean isCamera(Component component) {
        return Camera.class.isAssignableFrom(component.getClass());
    }

    private boolean isDrawableModel(Component component) {
        return component.hasProperty(RenderableMeshProperty.MESH_PROPERTY_NAME);
    }

    private SpaceSceneLoader getSceneLoader(RenderingConfig config, GameContextBuilder contextBuilder) {
        return new SpaceSceneLoader(config, contextBuilder);
    }

    public static void main(String... args) throws Exception {
        Application.launch(IDELauncher.class, args);
    }
}
