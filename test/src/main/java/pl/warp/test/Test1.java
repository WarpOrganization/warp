package pl.warp.test;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.component.SceneComponent;
import pl.warp.engine.core.component.SceneHolder;
import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.graphics.GraphicsThread;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.MeshProperty;
import pl.warp.engine.graphics.mesh.shapes.SphereBuilder;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.utility.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class Test1 {

    public static final Display DISPLAY = new Display(false, 1280, 920);


    public static void main(String[] args) {
        System.out.println();
        EngineContext engineContext = new EngineContext();
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();
        setupScene(engineContext, thread);
        setupCamera(engineContext);
    }

    private static void setupScene(EngineContext engineContext, GraphicsThread thread) {
        SceneHolder sceneHolder = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get();
        Scene scene = sceneHolder.getScene();
        createModels(scene, thread);

    }

    private static Component createModels(Scene scene, GraphicsThread graphicsThread) {
        Component ship = new SceneComponent(scene);
        Component sphere = new SceneComponent(scene);


        graphicsThread.scheduleOnce( () -> {
            createShip(ship);
            createSphere(sphere);
        });

        return ship;
    }

    private static void createShip(Component ship) {
        Mesh mesh = ObjLoader.read(
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

        TransformProperty property = new TransformProperty();
        ship.addProperty(property);
        property.move(new Vector3f(0, 0, -10f));
    }

    private static void createSphere(Component sphere) {
        Mesh mesh = SphereBuilder.createShape(64, 64, 5.0f);
        MeshProperty meshProperty = new MeshProperty(mesh);
        sphere.addProperty(meshProperty);

        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("wood/wood-stack-1-DIFFUSE.png"),
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
        sphere.addProperty(materialProperty);

        TransformProperty property = new TransformProperty();
        sphere.addProperty(property);
        property.move(new Vector3f(-10f, 0, -10f));
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
}
