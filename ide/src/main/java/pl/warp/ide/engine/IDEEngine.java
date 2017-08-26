package pl.warp.ide.engine;

import javafx.scene.canvas.Canvas;
import org.joml.Vector3f;
import pl.warp.engine.common.input.Input;
import pl.warp.engine.common.properties.TransformProperty;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.game.GameContextBuilder;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.engine.game.scene.GameSceneComponent;
import pl.warp.engine.game.scene.GameSceneLoader;
import pl.warp.ide.controller.camera.FreeCameraScript;
import pl.warp.ide.controller.sceneeditor.IDEComponentProperty;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 13
 */
public class IDEEngine {

    private GameSceneLoader loader;
    private SceneViewRenderer renderer;
    private RenderingConfig config;
    private GameContextBuilder contextBuilder;
    private Input input;

    private QuaternionCamera camera;
    private GameComponent cameraComponent;
    private IDEEngineTaskManager taskManager;
    private GameScene currentScene;

    public IDEEngine(GameSceneLoader loader, SceneViewRenderer renderer, RenderingConfig config, GameContextBuilder contextBuilder, Input input) {
        this.loader = loader;
        this.renderer = renderer;
        this.config = config;
        this.contextBuilder = contextBuilder;
        this.input = input;
    }

    public void start(Canvas canvas) {
        loader.loadScene();
        this.currentScene = loader.getScene();
        bindSizes(canvas);
        initCamera();
        taskManager = new IDEEngineTaskManager(renderer, currentScene, cameraComponent, config, contextBuilder, input);
        taskManager.startTasks(canvas);
        enableControls();
        loader.loadGraphics(taskManager.getGraphics().getThread());
    }

    protected void enableControls() {
        new FreeCameraScript(cameraComponent);
    }

    private void bindSizes(Canvas canvas) {
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            resize((int) canvas.getWidth(), (int) canvas.getHeight());
        });
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            resize((int) canvas.getWidth(), (int) canvas.getHeight());
        });
    }

    private void resize(int width, int height) {
        Graphics graphics = contextBuilder.getGameContext().getGraphics();
        graphics.getThread().scheduleOnce(() ->
                graphics.getEnginePipeline().resize(width, height)
        );
    }

    private void initCamera() {
        Display display = config.getDisplay();
        ProjectionMatrix matrix = new PerspectiveMatrix(config.getFov(), 0.01f, 20000f, display.getWidth(), display.getHeight());
        this.cameraComponent = new GameSceneComponent(currentScene);
        TransformProperty cameraTransform = new TransformProperty();
        cameraComponent.addProperty(cameraTransform);
        this.camera = new QuaternionCamera(cameraComponent, new Vector3f(0), cameraTransform, matrix);
        CameraProperty cameraProperty = new CameraProperty(camera);
        cameraComponent.addProperty(cameraProperty);
        cameraComponent.addProperty(new IDEComponentProperty());
    }

    public GameScene getScene() {
        return currentScene;
    }
}
