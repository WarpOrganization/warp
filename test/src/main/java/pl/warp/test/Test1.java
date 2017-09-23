package pl.warp.test;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.component.SceneComponent;
import pl.warp.engine.core.component.SceneHolder;
import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.graphics.GraphicsThread;
import pl.warp.engine.graphics.RenderingTask;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.MeshProperty;
import pl.warp.engine.graphics.mesh.VAOMesh;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.utility.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.WindowTask;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class Test1 {

    public static final Display DISPLAY = new Display(false, 1024, 720);


    public static void main(String[] args) {

        EngineContext engineContext = new EngineContext();
        GraphicsThread thread = startGraphics(engineContext);
        setupScene(engineContext, thread);
        setupCamera(engineContext);

    }

    private static void setupScene(EngineContext engineContext, GraphicsThread thread) {
        SceneHolder sceneHolder = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get();
        Scene scene = sceneHolder.getScene();
        createShip(scene, thread);

    }

    private static Component createShip(Scene scene, GraphicsThread graphicsThread) {
        Component ship = new SceneComponent(scene);

        graphicsThread.scheduleOnce( () -> {

            VAOMesh mesh = ObjLoader.read(
                    Test1.class.getResourceAsStream("fighter_1.obj"),
                    true).toVAOMesh();
            MeshProperty meshProperty = new MeshProperty(mesh);
            ship.addProperty(meshProperty);

            ImageData imageData = ImageDecoder.decodePNG(
                    Test1.class.getResourceAsStream("fighter_1.png"),
                    PNGDecoder.Format.RGBA
            );
            Texture2D diffuse = new Texture2D(
                    imageData.getHeight(),
                    imageData.getHeight(),
                    GL11.GL_RGBA16,
                    GL11.GL_RGBA,
                    true,
                    imageData.getData());
            Material material = new Material(diffuse, null, null);
            MaterialProperty materialProperty = new MaterialProperty(material);
            ship.addProperty(materialProperty);

            ship.addProperty(new TransformProperty());
        });

        return ship;
    }

    private static void setupCamera(EngineContext engineContext) {
        Scene scene = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get().getScene();
        Component cameraComponent = new SceneComponent(scene);
        cameraComponent.addProperty(new TransformProperty());
        cameraComponent.addScript(SimpleControlScript.class);

        CameraHolder cameraHolder = engineContext.getLoadedContext()
                .findOne(CameraHolder.class)
                .get();
        PerspectiveMatrix projection = new PerspectiveMatrix(
                45f,
                0.01f,
                100f,
                DISPLAY.getWidth(),
                DISPLAY.getHeight()
        );
        Camera camera = new QuaternionCamera(cameraComponent, new TransformProperty(), projection);
        cameraHolder.setCamera(camera);

    }

    private static GraphicsThread startGraphics(EngineContext engineContext) {
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();

        WindowTask windowTask = engineContext.getLoadedContext()
                .findOne(WindowTask.class).get();
        windowTask.setDisplay(DISPLAY);
        thread.scheduleTask(windowTask);

        RenderingTask renderingTask = engineContext.getLoadedContext()
                .findOne(RenderingTask.class).get();
        thread.scheduleTask(renderingTask);

        thread.start();
        return thread;
    }


}
