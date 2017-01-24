package pl.warp.ide.engine;

import javafx.scene.canvas.Canvas;
import pl.warp.engine.ai.AITask;
import pl.warp.engine.audio.*;
import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.script.ScriptTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.pipeline.output.OutputTexture2DRenderer;
import pl.warp.engine.physics.DefaultCollisionStrategy;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.RayTester;
import pl.warp.ide.scene.SceneLoader;


/**
 * @author Jaca777
 *         Created 2017-01-22 at 13
 */
public class EngineIDEInitializer {

    private SceneLoader loader;
    private Canvas destCanvas;
    private SceneViewRenderer sceneViewRenderer;
    private RenderingConfig config;
    private Input input;

    public EngineIDEInitializer(SceneLoader loader, Canvas destCanvas, RenderingConfig config, Input input) {
        this.loader = loader;
        this.destCanvas = destCanvas;
        this.config = config;
        this.input = input;

    }

    public void start(){
        EngineContext context = new EngineContext();
        OutputTexture2DRenderer outputRenderer = new OutputTexture2DRenderer();
        this.sceneViewRenderer = new SceneViewRenderer(destCanvas, outputRenderer.getOutput());
        Graphics graphics = createGraphics(context, outputRenderer);
        Scene scene = loader.getScene();
        startTasks(context, graphics, scene);
    }

    private Graphics createGraphics(EngineContext context, OutputTexture2DRenderer outputRenderer) {
        loader.loadScene();
        Graphics graphics = new Graphics(context, outputRenderer, loader.getCamera(), config);
        loader.loadGraphics(graphics.getThread());
        return graphics;
    }

    private void startTasks(EngineContext context, Graphics graphics, Scene scene) {
        Component root = scene.getChild(0);
        createScriptThread(context, input, graphics.getThread());
        createPhysicsThread(root, graphics.getThread());
        createAudioThread();
        createAIThread(root);
        graphics.create();
        sceneViewRenderer.startRendering();
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
        audioThread.scheduleOnce(() -> {
            audioThread.scheduleTask(new AudioTask(audioContext));
            audioManager.loadFiles("/pl/warp/game/sound");
            audioThread.scheduleTask(new AudioPosUpdateTask(audioContext));

        });
        audioThread.start();
    }
}
