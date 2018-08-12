package net.warpgame.servertest.client;

import net.warpgame.engine.console.ConsoleService;
import net.warpgame.engine.console.command.SimpleCommand;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.graphics.GraphicsThread;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSource;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSourceProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.SceneLightManager;
import net.warpgame.engine.graphics.resource.texture.ImageDataArray;
import net.warpgame.engine.graphics.resource.texture.ImageDecoder;
import net.warpgame.engine.graphics.resource.texture.PNGDecoder;
import net.warpgame.engine.graphics.texture.Cubemap;
import net.warpgame.engine.graphics.window.Display;
import net.warpgame.engine.graphics.window.WindowManager;
import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class ClientTest {

    public static final Display DISPLAY = new Display(false, 1280, 720);
    private static ConsoleService consoleService;

    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        EngineContext engineContext = new EngineContext("dev", "simplePhysics", "client", "graphics");
        engineContext.getLoadedContext().addService(engineRuntime.getIdRegistry());
        engineContext.getLoadedContext().findOne(ConsoleService.class).get().init();
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();
        consoleService = engineContext.getLoadedContext()
                .findOne(ConsoleService.class)
                .get();
        setupScene(engineContext, thread);
        registerCommandsAndVariables(engineContext.getLoadedContext());
    }

    private static void setupListeners(Component root, EngineContext context) {
        root.addListener(new ShipLoadListener(root, context.getLoadedContext().findOne(GraphicsThread.class).get()));
        CameraHolder cameraHolder = context.getLoadedContext().findOne(CameraHolder.class).get();
        root.addListener(new BoardShipListener(root, cameraHolder, DISPLAY, context.getComponentRegistry()));
        root.addListener(new TestKeyboardListener(root, context.getLoadedContext().findOne(WindowManager.class).get()));
    }

    private static void setupScene(EngineContext engineContext, GraphicsThread thread) {
        SceneHolder sceneHolder = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get();
        Scene scene = sceneHolder.getScene();
        makeLight(new SceneComponent(scene, 1000000010));
        createCubemap(scene, thread);
        setupListeners(scene, engineContext);
    }

    private static void registerCommandsAndVariables(Context context) {
        consoleService.init();
        SimpleCommand exit = new SimpleCommand("quit",
                "Stops the engine and quits",
                "quit");
        exit.setExecutor((args) -> {
            context.findAll(EngineThread.class).forEach(EngineThread::interrupt);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        consoleService.registerCommand(exit);

        CameraHolder ch = context.findOne(CameraHolder.class).get();
        SceneHolder sh = context.findOne(SceneHolder.class).get();
    }

    private static void createCubemap(Scene scene, GraphicsThread thread) {
        thread.scheduleOnce(() -> {
            ImageDataArray imageDataArray = ImageDecoder.decodeCubemap("net/warpgame/servertest/client/stars3", PNGDecoder.Format.RGBA);
            Cubemap cubemap = new Cubemap(imageDataArray.getWidth(), imageDataArray.getHeight(), imageDataArray.getData());
            CubemapProperty cubemapProperty = new CubemapProperty(cubemap);
            scene.addProperty(cubemapProperty);
        });
    }

    private static void makeLight(Component component) {
        SceneLightManager sceneLightManager = component.getContext()
                .getLoadedContext()
                .findOne(SceneLightManager.class)
                .get();
        LightSource lightSource = new LightSource(new Vector3f(1.3f, 1.3f, 1.3f).mul(20));
        LightSourceProperty lightSourceProperty = new LightSourceProperty(lightSource);
        component.addProperty(lightSourceProperty);
        sceneLightManager.addLight(lightSourceProperty);
    }

    private static void createModels(Scene scene, GraphicsThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {
            Component lsource = new SceneComponent(scene);
            lsource.addProperty(new TransformProperty().move(new Vector3f(0, 10, 0)));
            makeLight(lsource);
        });

    }


}
