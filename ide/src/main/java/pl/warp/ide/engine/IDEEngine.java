package pl.warp.ide.engine;

import javafx.scene.canvas.Canvas;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;
import pl.warp.engine.graphics.window.Display;
import pl.warp.game.GameContextBuilder;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.scene.GameSceneLoader;

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
        initCamera();
        taskManager = new IDEEngineTaskManager(renderer, currentScene, cameraComponent, config, contextBuilder, input);
        taskManager.startTasks(canvas);
        loader.loadGraphics(taskManager.getGraphics().getThread());
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
        new IDECameraControlScript(cameraComponent);
    }

    public GameScene getScene() {
        return currentScene;
    }
}
