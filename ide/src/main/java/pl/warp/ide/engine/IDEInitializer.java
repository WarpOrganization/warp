package pl.warp.ide.engine;

import javafx.scene.canvas.Canvas;
import pl.warp.engine.ai.AITask;
import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.audio.AudioPosUpdateTask;
import pl.warp.engine.audio.AudioTask;
import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.script.ScriptTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.GraphicsSceneLoader;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.pipeline.output.OutputTexture2DRenderer;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.physics.DefaultCollisionStrategy;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.RayTester;

import java.io.File;


/**
 * @author Jaca777
 *         Created 2017-01-22 at 13
 * Loads scene and starts the tasks
 * */
public class IDEInitializer {

    private SceneViewRenderer sceneViewRenderer;
    private GraphicsSceneLoader loader;
    private Scene loadedScene;

    private RenderingConfig config;
    private EngineContext context;
    private Graphics graphics;

    private Input input;

    public IDEInitializer(GraphicsSceneLoader loader, SceneViewRenderer sceneViewRenderer, RenderingConfig config, EngineContext context, Input input) {
        this.loader = loader;
        this.sceneViewRenderer = sceneViewRenderer;
        this.config = config;
        this.context = context;
        this.input = input;
    }

    public void start(Canvas destCanvas){
        setRenderingTargetSize((int) destCanvas.getWidth(), (int) destCanvas.getHeight());
        OutputTexture2DRenderer outputRenderer = new OutputTexture2DRenderer();
        loadScene();
        graphics = createGraphics(context, outputRenderer);
        loader.loadGraphics(graphics.getThread());
        startTasks(context, graphics, loadedScene, destCanvas);
    }

    private void setRenderingTargetSize(int width, int height) {
        Display display = this.config.getDisplay();
        display.setWidth(width + 1);
        display.setHeight(height + 1);
    }

    private void loadScene() {
        loader.loadScene();
        this.loadedScene = loader.getScene();
    }

    private Graphics createGraphics(EngineContext context, OutputTexture2DRenderer outputRenderer) {
        return new Graphics(context, outputRenderer, loader.getCamera(), config);
    }

    private void startTasks(EngineContext context, Graphics graphics, Scene scene, Canvas destCanvas) {
        Component root = scene.getChild(0);
        createScriptThread(context, input, graphics.getThread());
        createPhysicsThread(root, graphics.getThread());
        createAudioThread();
        createAIThread(root);
        graphics.create();
        OutputTexture2DRenderer output = (OutputTexture2DRenderer) graphics.getOutput();
        sceneViewRenderer.startRendering(output.getOutput(), destCanvas);
    }

    private void createPhysicsThread(Component root, EngineThread graphicsThread) {
        EngineThread physicsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        RayTester rayTester = new RayTester();
        physicsThread.scheduleOnce(() -> {
            physicsThread.scheduleTask(new MovementTask(root));
            physicsThread.scheduleTask(new PhysicsTask(new DefaultCollisionStrategy(), root, rayTester));
        });
        graphicsThread.scheduleOnce(physicsThread::start);
    }

    private void createScriptThread(EngineContext context, Input input, EngineThread graphicsThread) {
        EngineThread scriptThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        graphicsThread.scheduleOnce(() -> {
            context.setInput(input);
            scriptThread.scheduleTask(new ScriptTask(context.getScriptContext()));
            //TODO start input task
            scriptThread.start(); //has to start after the window is created
        });
    }

    private void createAIThread(Component root) {
        EngineThread aiThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        aiThread.scheduleOnce(() -> aiThread.scheduleTask(new AITask(root)));
        aiThread.start();
    }

    private void createAudioThread() {
        AudioContext audioContext = new AudioContext();
        AudioManager audioManager = new AudioManager(audioContext);
        EngineThread audioThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        audioThread.scheduleTask(new AudioTask(audioContext));
        audioThread.scheduleTask(new AudioPosUpdateTask(audioContext));
        audioThread.scheduleOnce(() -> {
            audioManager.loadFiles("data" + File.separator + "sound" + File.separator + "effects"); //TODO move it
        });
        audioThread.start();
    }

    public Scene getLoadedScene() {
        return loadedScene;
    }
}
