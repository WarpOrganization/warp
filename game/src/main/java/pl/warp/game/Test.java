package pl.warp.game;

import org.apache.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.ai.AITask;
import pl.warp.engine.audio.*;
import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.script.ScriptTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.input.glfw.GLFWInput;
import pl.warp.engine.graphics.input.glfw.GLFWInputTask;
import pl.warp.engine.graphics.pipeline.OnScreenRenderer;
import pl.warp.engine.graphics.pipeline.output.OutputTexture2DRenderer;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;
import pl.warp.engine.physics.DefaultCollisionStrategy;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.RayTester;
import pl.warp.ide.Launcher;
import pl.warp.ide.controller.IDEController;
import pl.warp.ide.scene.SceneLoader;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 14
 *         CANCER CODE, ONLY FOR TESTING
 *         TODO KILL IT WITH FIRE
 */
public class Test {

    private static Logger logger = Logger.getLogger(Test.class);
    private static final boolean FULLSCREEN = false;
    private static final int WIDTH = 864, HEIGHT = 697;
    private static final float ROT_SPEED = 0.05f;
    private static final float MOV_SPEED = 0.2f * 10;
    private static final float BRAKING_FORCE = 0.2f * 10;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 5;
    private static Random random = new Random();

    public static void main(String... args) {

        EngineContext context = new EngineContext();
        RenderingConfig settings = new RenderingConfig(60, new Display(FULLSCREEN, WIDTH, HEIGHT));

        OutputTexture2DRenderer outputRenderer = new OutputTexture2DRenderer();
        IDEController.INPUT = outputRenderer.getOutput();
        OnScreenRenderer onScreenRenderer = new OnScreenRenderer();
        //CameraScript cameraScript = new CameraScript(camera);
        GLFWInput input = new GLFWInput();
        AudioContext audioContext = new AudioContext();
        AudioManager audioManager = new AudioManager(audioContext);
        SceneLoader loader = new TestSceneLoader(settings, context, audioManager);
        loader.loadScene();
        Camera camera = loader.getCamera();
        Scene scene = loader.getScene();
        audioContext.setAudioListener(new AudioListener(camera.getParent()));
        Graphics graphics = new Graphics(context, outputRenderer, camera, settings);
        EngineThread graphicsThread = graphics.getThread();
        graphics.enableUpsLogging();
        loader.loadGraphics(graphicsThread);

        Component root = scene.getChild(0);

        EngineThread scriptsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        graphicsThread.scheduleOnce(() -> {
            context.setInput(input);
            scriptsThread.scheduleTask(new ScriptTask(context.getScriptContext()));
            GLFWWindowManager windowManager = graphics.getWindowManager();
            scriptsThread.scheduleTask(new GLFWInputTask(input, windowManager));
            scriptsThread.start(); //has to start after the window is created
        });
        EngineThread physicsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        RayTester rayTester = new RayTester();
        physicsThread.scheduleOnce(() -> {
            physicsThread.scheduleTask(new MovementTask(root));
            physicsThread.scheduleTask(new PhysicsTask(new DefaultCollisionStrategy(), root, rayTester));
        });

        EngineThread audioThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        audioThread.scheduleOnce(() -> {
            audioThread.scheduleTask(new AudioTask(audioContext));
            audioManager.loadFiles("/pl/warp/game/sound");
            audioThread.scheduleTask(new AudioPosUpdateTask(audioContext));

        });
        audioThread.start();
        EngineThread aiThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        aiThread.scheduleOnce(() -> {
            aiThread.scheduleTask(new AITask(root));
            new Thread(() -> {
                try {
                    Launcher.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        aiThread.start();
        new Script(root) {
            @Override
            public void onInit() {

            }

            @Override
            public void onUpdate(int delta) {
                if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                    scriptsThread.scheduleOnce(scriptsThread::interrupt);
                    graphicsThread.scheduleOnce(graphicsThread::interrupt);
                    physicsThread.scheduleOnce(physicsThread::interrupt);
                }
            }
        };

        graphicsThread.scheduleOnce(physicsThread::start);

        graphics.create();
    }
}
