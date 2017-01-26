package pl.warp.ide.engine;

import javafx.scene.canvas.Canvas;
import org.joml.Vector3f;
import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.graphics.GraphicsSceneLoader;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 13
 */
public class IDEEngine {

    private GraphicsSceneLoader loader;
    private SceneViewRenderer renderer;
    private RenderingConfig config;
    private EngineContext context;
    private Input input;

    private QuaternionCamera camera;
    private IDEEngineTaskManager taskManager;
    private Scene currentScene;

    public IDEEngine(GraphicsSceneLoader loader, SceneViewRenderer renderer, RenderingConfig config, EngineContext context, Input input) {
        this.loader = loader;
        this.renderer = renderer;
        this.config = config;
        this.context = context;
        this.input = input;
    }

    public void start(Canvas canvas) {
        loader.loadScene();
        this.currentScene = loader.getScene();
        initCamera();
        taskManager = new IDEEngineTaskManager(renderer, currentScene, camera, config, context, input);
        taskManager.startTasks(canvas);
        loader.loadGraphics(taskManager.getGraphics().getThread());
    }

    private void initCamera() {
        Display display = config.getDisplay();
        ProjectionMatrix matrix = new PerspectiveMatrix(config.getFov(), 0.01f, 20000f, display.getWidth(), display.getHeight());
        this.camera = new QuaternionCamera(currentScene, new Vector3f(0), matrix);
        new IDECameraControlScript(camera);
        camera.addProperty(new IDEComponentProperty());
    }

    public Scene getScene() {
        return currentScene;
    }
}
