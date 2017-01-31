package pl.warp.test;

import org.apache.log4j.Logger;
import org.joml.Vector3f;
import pl.warp.engine.ai.AITask;
import pl.warp.engine.audio.*;
import pl.warp.engine.audio.playlist.PlayList;
import pl.warp.engine.audio.playlist.PlayRandomPlayList;
import pl.warp.engine.core.EngineThread;
import pl.warp.engine.core.RapidExecutionStrategy;
import pl.warp.engine.core.SyncEngineThread;
import pl.warp.engine.core.SyncTimer;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.script.ScriptTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.input.glfw.GLFWInput;
import pl.warp.engine.graphics.input.glfw.GLFWInputTask;
import pl.warp.engine.graphics.pipeline.OnScreenRenderer;
import pl.warp.engine.graphics.window.GLFWWindowManager;
import pl.warp.engine.physics.DefaultCollisionStrategy;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.RayTester;
import pl.warp.game.GameContext;
import pl.warp.game.GameContextBuilder;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.CameraRayTester;

import java.awt.event.KeyEvent;
import java.io.File;
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
    private static final float ROT_SPEED = 0.05f;
    private static final float MOV_SPEED = 0.2f * 10;
    private static final float BRAKING_FORCE = 0.2f * 10;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 5;
    private static Random random = new Random();

    public static void runTest(RenderingConfig config) {

        GameContextBuilder contextBuilder = new GameContextBuilder();
        GameContext context = contextBuilder.getGameContext();

        OnScreenRenderer onScreenRenderer = new OnScreenRenderer(config);

        AudioContext audioContext = new AudioContext();
        AudioManager.INSTANCE = new AudioManager(audioContext);
        TestSceneLoader loader = new TestSceneLoader(config, contextBuilder);
        loader.loadScene();
        GameComponent cameraComponent = loader.getCameraComponent();
        Camera camera = cameraComponent.<CameraProperty>getProperty(CameraProperty.CAMERA_PROPERTY_NAME).getCamera();
        contextBuilder.setCamera(camera);
        //new GoatControlScript(cameraComponent.getParent(), MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);
        Scene scene = loader.getScene();
        GLFWInput input = new GLFWInput(scene);
        audioContext.setAudioListener(new AudioListener(cameraComponent.getParent()));
        Graphics graphics = new Graphics(context, onScreenRenderer, camera, config);
        contextBuilder.setGraphics(graphics);
        EngineThread graphicsThread = graphics.getThread();
        graphics.enableUpsLogging();
        loader.loadGraphics(graphicsThread);


        EngineThread scriptsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        graphicsThread.scheduleOnce(() -> {
            contextBuilder.setInput(input);
            scriptsThread.scheduleTask(new ScriptTask(context.getScriptManager()));
            GLFWWindowManager windowManager = graphics.getWindowManager();
            scriptsThread.scheduleTask(new GLFWInputTask(input, windowManager));
            scriptsThread.start(); //has to start after the window is created
        });


        EngineThread physicsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        RayTester rayTester = new RayTester();
        contextBuilder.setRayTester(new CameraRayTester(context, rayTester));
        physicsThread.scheduleTask(new MovementTask(scene));
        physicsThread.scheduleTask(new PhysicsTask(new DefaultCollisionStrategy(), scene, rayTester));


        EngineThread audioThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        audioThread.scheduleTask(new AudioTask(audioContext));
        audioThread.scheduleTask(new AudioPosUpdateTask(audioContext));

        audioThread.scheduleOnce(() -> {
            AudioManager.INSTANCE.loadFiles("data" + File.separator + "sound" + File.separator + "effects");
            PlayList playList = new PlayRandomPlayList();
            playList.add("data" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-01_Red_Giant.wav");
            playList.add("data" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-05_In_Time.wav");
            MusicSource musicSource = AudioManager.INSTANCE.createMusicSource(new Vector3f(), playList);
            AudioManager.INSTANCE.play(musicSource);
        });

        audioThread.start();

        EngineThread aiThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());

        aiThread.scheduleTask(new AITask(scene));
        aiThread.start();
        new Script(scene) {
            @Override
            public void onInit() {

            }

            @Override
            public void onUpdate(int delta) {
                if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    scriptsThread.scheduleOnce(scriptsThread::interrupt);
                    graphicsThread.scheduleOnce(graphicsThread::interrupt);
                    physicsThread.scheduleOnce(physicsThread::interrupt);
                    System.exit(0);
                }
            }
        };
        loader.enableControls();
/*        graphicsThread.scheduleOnce(() -> {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
        });*/ //MATRIX MODE
        graphicsThread.scheduleOnce(physicsThread::start);
        graphics.create();
    }


}
